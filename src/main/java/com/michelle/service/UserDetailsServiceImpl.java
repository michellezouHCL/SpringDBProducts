package com.michelle.service;

import org.omg.CORBA.UserException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.michelle.model.User;
import com.michelle.repo.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository ur;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = ur.getUserByUsername(username);

		if (user == null)
			throw new UsernameNotFoundException("Could not find user");

		return new MyUserDetails(user);
	}
	/*
    public void register(User user) throws Exception {

        //Let's check if user already registered with us
        if(ur.getUserByUsername(user.getUsername())==null){
            throw new Exception("User already exists for this email");
        }
        User u = new User();
        u.setUsername(user.getUsername());
        u.setPassword(user.getPassword());
        u.setRole(user.getRole());
        ur.save(u);
    }*/
}
