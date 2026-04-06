package com.ss.ManagerController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ss.users.UsersTable;

import jakarta.servlet.http.HttpSession;

@Controller
public class ManagerController {
	
	@GetMapping("/")
	public String GoToHome() {
		
		return "Home";
	}
	
	@GetMapping("/getAdminDashboard")
	public String getAdminDashboard(HttpSession session, Model model) {

	    UsersTable userLoged = (UsersTable) session.getAttribute("loggedUser");

	    model.addAttribute("loggedUser", userLoged);

	    return "sidebar";
	}
	
	
}
