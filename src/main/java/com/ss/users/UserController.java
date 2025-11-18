package com.ss.users;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;

	@RequestMapping("user_login")
	public String LoginUser() {
		
		return "Login";
	}
	
	@PostMapping("/createUsers")
	@ResponseBody
	public UsersTable createUser(@RequestBody UsersTable user, Model model) {
		
		UsersTable users=userService.saveUser(user);
		
		return users;
	}
	
	@PostMapping("/verifylogin")
	public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> loginData) {
	    String username = loginData.get("username");
	    String password = loginData.get("password");

	    Optional<UsersTable> userOpt = userService.CheckUser(username, password);
	    
	    
	    Map<String, Object> response = new HashMap();
	    if (userOpt.isPresent()) {
	        response.put("status", "success");
	        response.put("user", userOpt.get());
	        return ResponseEntity.ok(response);
	    } else {
	        response.put("status", "error");
	        response.put("message", "Invalid username or password");
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
	    }
	}

}
