package com.ss.ManagerController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ManagerController {
	
	@GetMapping("/")
	public String GoToHome() {
		
		return "Home";
	}
	
	@GetMapping("/getAdminDashboard")
	public String getAdminDashboard() {
		
		return "sidebar";
	}
	
}
