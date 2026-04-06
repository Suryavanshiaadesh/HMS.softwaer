package com.ss.users;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ss.student.StudentService;
import com.ss.student.Students;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
	@Autowired
	StudentService studentServ;
	@Autowired
	private UserService userService;

	@RequestMapping("user_login")
	public String LoginUser() {
		
		System.out.println(userService.getAllUsers());
		
		return "Login";
	}
	
	@PostMapping("/createUsers")
	@ResponseBody
	public UsersTable createUser(@RequestBody UsersTable user, Model model) {
		
		UsersTable users=userService.saveUser(user);
		
		return users;
	}
	
	@PostMapping("/verifylogin")
	public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> loginData,HttpSession session) {
	    String username = loginData.get("username");
	    String password = loginData.get("password");

	    Optional<UsersTable> userOpt = userService.CheckUser(username, password);
	    
	    session.setAttribute("loggedUser", userOpt.get());
	    Map<String, Object> response = new HashMap();
	    if (userOpt.isPresent()) {
	        response.put("status", "success");
	        Optional<Students> users=studentServ.getStudentByPhone(Long.valueOf(userOpt.get().getUserName()));
	        response.put("user", userOpt.get());
	        
	        if(!userOpt.get().getRoll().equals("Admin")) {
//	    	    session.setAttribute("loggedUser", users.get());	        	
	        	response.put("userName",users.get());
	        	
	        }
	        return ResponseEntity.ok(response);
	    } else {
	        response.put("status", "error");
	        response.put("message", "Invalid username or password");
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
	    }
	}

}
