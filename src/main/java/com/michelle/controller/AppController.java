package com.michelle.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.michelle.exception.ProdNotFoundException;
import com.michelle.model.Category;
import com.michelle.model.OrderProdList;
import com.michelle.model.Product;
import com.michelle.service.CategoryService;
import com.michelle.service.OrderProdListService;
import com.michelle.service.ProdService;

@Controller
public class AppController {

	@Autowired
	ProdService ps;

	@Autowired
	OrderProdListService os;

	@Autowired
	CategoryService cs;

	@RequestMapping("/")
	public String viewHomePage(Model model) {
		List<Product> listProducts = ps.listAll();
		model.addAttribute("listProducts", listProducts);
		List<Category> categories = cs.listAll();
	    model.addAttribute("categories", categories);
		return "index";
	}

	@RequestMapping("/new")
	public String showNewProductForm(Model model) {
		Product product = new Product();
		model.addAttribute("product", product);
		List<Category> categories = cs.listAll();
	    model.addAttribute("categories", categories);
		return "new_product";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveProduct(@ModelAttribute("product") Product product) {
		ps.save(product);

		return "redirect:/";
	}

//	public List<Category> getCategories() {
//		List<Category> list = new ArrayList<Category>();
//		list.add(new Category("", "Shirts"));
//		list.add(new Category("", "Pants"));
//		list.add(new Category("", "Socks"));
//		list.add(new Category("", "Shoes"));
//		return list;
//	}

	@RequestMapping("/edit/{id}")
	public ModelAndView showEditProductForm(@PathVariable(name = "id") Long id) {
		ModelAndView mav = new ModelAndView("edit_product");

		Product product = ps.getProdById(id);
		mav.addObject("product", product);
		
		List<Category> categories = cs.listAll();
	    mav.addObject("categories", categories);
		return mav;
	}

	@RequestMapping("/delete/{id}")
	public String deleteProduct(@PathVariable(name = "id") Long id) {
		ps.deleteProd(id);

		return "redirect:/";
	}

	@RequestMapping("/addOne/{id}")
	public String addOneProd(@PathVariable(name = "id") Long id) throws ProdNotFoundException {
		OrderProdList p1 = os.addOne(id);
		os.save(p1);

		return "redirect:/cart";
	}

	@RequestMapping("/minusOne/{id}")
	public String minusOneProd(@PathVariable(name = "id") Long id) throws ProdNotFoundException {
		OrderProdList p1 = os.minusOne(id);
		os.save(p1);
		return "redirect:/cart";
	}

	@RequestMapping("/clear/{id}")
	public String clearCart(@PathVariable(name = "id") Long id) throws ProdNotFoundException {
		OrderProdList p1 = os.clearCart(id);
		os.save(p1);
		return "redirect:/cart";
	}

	@RequestMapping("/cart")
	public String cart(Model m) {
		List<OrderProdList> ol = os.getAll();
		m.addAttribute("orderProdList", ol);
		return "user_cart";

	}

	@RequestMapping(value = "/addToCart/{id}")
	public String addToCart(@PathVariable("id") Long id) {
		Product product = ps.getProdById(id);
		os.addToCart(product);
		return "redirect:/cart";
	}

	@RequestMapping(value = "/deleteProdFromCart/{id}")
	public String deleteProdFromCart(@PathVariable("id") Long id) {
		os.deleteOPLByProdId(id);
		return "redirect:/cart";
	}
}
