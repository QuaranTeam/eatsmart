package com.eatSmart.Controllers;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import com.eatSmart.User;
import com.eatSmart.UserRepository;

@Controller
public class LoginRegisterController {

	
	@Resource
	private UserRepository userRepo;
	
	@RequestMapping("/login")
	public String showLogin(){
		return "login";	
	}
	
	 @GetMapping("/login-error")
	  public String loginError(Model model) {
	    model.addAttribute("loginError", true);
	    return "login";
	 }
	
	

	@RequestMapping("/login-user")
	public String loginUser(String username, String password) {
		User user = userRepo.findByUsername(username);
		
		if(user==null) {
			return"redirect:login-error";
		}
		
		return "redirect:/";
	}
	
	
	
	
	
	@RequestMapping("/register")
	public String showRegister() {
		return "register";
	}
	
	
	@RequestMapping("/add-user")
	public String addUser(String firstName, String lastName, String username, String password) {
		User user = userRepo.findByUsername(username);
		if(user==null) {
			user = new User(firstName, lastName, username, password);
			user = userRepo.save(user);
		}
		return "redirect:/login";
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
