package com.michelle.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.michelle.exception.ProdNotFoundException;
import com.michelle.model.Category;
import com.michelle.model.OrderProdList;
import com.michelle.model.Product;

import com.michelle.model.User;
import com.michelle.repo.UserRepository;

import com.michelle.service.CategoryService;
import com.michelle.service.OrderProdListService;
import com.michelle.service.ProdService;
import com.michelle.service.UserDetailsServiceImpl;

import com.michelle.service.MyUserDetails;

@Controller
public class AppController {

	@Autowired
	UserDetailsServiceImpl us;

	@Autowired
	ProdService ps;

	@Autowired
	OrderProdListService os;

	@Autowired
	CategoryService cs;

	@Autowired
	UserRepository userRepository;

	@RequestMapping("/login")
	public String login() {
		return "login";
	}

	@RequestMapping("/")
	public String sendToPage(Authentication auth) {
		if (!auth.isAuthenticated()) {
			return "redirect:/login?error";
		} else if (((MyUserDetails) auth.getPrincipal()).hasRole("ROLE_ADMIN")) {
			return "redirect:/admin/home";
		} else {
			return "redirect:/user/home";
		}
	}

	@RequestMapping("/registration")
	public String showRegistrationForm(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "registration";
	}

	@PostMapping("/process_registration")
	public String prcoessRegistration(User user) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);

		userRepository.save(user);
		return "register_success";
	}

	@RequestMapping("/logout")
	public String logout() {
		return "logout";
	}

	@RequestMapping("/admin/home")
	public String viewHomePage(ModelAndView mv, Model m, String keyword) throws IOException {

		if (keyword != null) {
			if (ps.findByKeyword(keyword).isEmpty()) {

				m.addAttribute("adminlistProd", ps.findByCategory(keyword));
			} else {
				m.addAttribute("adminlistProd", ps.findByKeyword(keyword));
			}

		} else {
			List<Product> listProducts = ps.listAll();
			m.addAttribute("adminlistProd", listProducts);
		}

		return "/admin/home";
	}

	@RequestMapping(value = "/photoCat", produces = MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] photoCat(Long id) throws IOException {
		Product p = ps.getProdById(id);
		return org.apache.commons.io.IOUtils.toByteArray(new ByteArrayInputStream(p.getPhotos()));
	}

	@RequestMapping("/user/home")
	public String userHome(Model m, String keyword) {
		if (keyword != null) {
			// m.addAttribute("adminlistProd", ps.findByKeyword(keyword));
			if (ps.findByKeyword(keyword).isEmpty()) {
				m.addAttribute("listProducts", ps.findByCategory(keyword));
			} else {
				m.addAttribute("listProducts", ps.findByKeyword(keyword));
			}

		} else {
			List<Product> listProduct = ps.listAll();
			m.addAttribute("listProducts", listProduct);
		}
		return "/user/home";
	}

	@RequestMapping("/admin/new")
	public String showNewProductForm(Model model) {
		Product product = new Product();
		model.addAttribute("product", product);
		List<Category> categories = cs.listAll();
		model.addAttribute("categories", categories);
		return "/admin/new_product";
	}

	@RequestMapping(value = "/admin/save", method = RequestMethod.POST)
	public RedirectView saveProduct(@ModelAttribute("product") Product product,
			@RequestParam("image") MultipartFile multipartFile) throws IOException {
		if(multipartFile.isEmpty()) {
			Product p =ps.getProdById(product.getProductId());
			product.setPhotos(p.getPhotos());
		} else {
			
			byte[] file = multipartFile.getBytes();
			product.setPhotos(file);
		}
		ps.save(product);

		OrderProdList o = os.findById(product.getProductId());
		if (o != null) {
			o.setCategory(product.getCategory());
			o.setProductName(product.getProductName());
			o.setProductPrice(product.getProductPrice());
			o.setInstockQty(product.getInstockQty());
			o.setPhotos(product.getPhotos());
			os.save(o);
		}
		return new RedirectView("/admin/home", true);
	}

//	public List<Category> getCategories() {
//		List<Category> list = new ArrayList<Category>();
//		list.add(new Category("", "Shirts"));
//		list.add(new Category("", "Pants"));
//		list.add(new Category("", "Socks"));
//		list.add(new Category("", "Shoes"));
//		return list;
//	}

	@RequestMapping("/admin/edit/{id}")
	public String showEditProductForm(@PathVariable(name = "id") Long id, Model m) {

		Product product = ps.getProdById(id);
		m.addAttribute("product", product);
		List<Category> categories = cs.listAll();

		m.addAttribute("categories", categories);
		return "/admin/edit_product";
	}

	@RequestMapping("/admin/delete/{id}")
	public String deleteProduct(@PathVariable(name = "id") Long id) {
		ps.deleteProd(id);
		os.deleteOPLByProdId(id);
		return "redirect:/admin/home";
	}

	@RequestMapping("/user/addOne/{id}")
	public String addOneProd(@PathVariable(name = "id") Long id) throws ProdNotFoundException {
		OrderProdList p1 = os.addOne(id);
		os.save(p1);
		return "redirect:/user/cart";
	}

	@RequestMapping("/user/minusOne/{id}")
	public String minusOneProd(@PathVariable(name = "id") Long id) throws ProdNotFoundException {
		OrderProdList p1 = os.minusOne(id);
		os.save(p1);
		return "redirect:/user/cart";
	}

	@RequestMapping("/user/clear/{id}")
	public String clearCart(@PathVariable(name = "id") Long id) throws ProdNotFoundException {
		OrderProdList p1 = os.clearCart(id);
		os.save(p1);
		return "redirect:/user/cart";
	}

	@RequestMapping("/user/cart")
	public String cart(Model m) {
		List<OrderProdList> ol = os.getAll();
		m.addAttribute("orderProdList", ol);
		return "/user/user_cart";

	}

	@RequestMapping(value = "/user/addToCart/{id}")
	public String addToCart(@PathVariable("id") Long id) {
		Product product = ps.getProdById(id);
		os.addToCart(product);
		return "redirect:/user/cart";
	}

	@RequestMapping(value = "/user/deleteProdFromCart/{id}")
	public String deleteProdFromCart(@PathVariable("id") Long id) {
		os.deleteOPLByProdId(id);
		return "redirect:/user/cart";
	}
}
