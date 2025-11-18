package com.ss.student;

import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.Base64;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ss.address.Address;

import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class StudentController {
	
	@Autowired
	StudentService studentService;
	
	@GetMapping("/add")
	public String showform(Model model) {
		model.addAttribute("student" ,new Students());
		return "studentForm";
	}
	
	@PostMapping("/saveStudentForm")
	@ResponseBody
	public Students saveStudent(@RequestBody Map<String, Object> studentMap) {
	    try {
	        Students student = new Students();
	        student.setName((String) studentMap.get("name"));
	        student.setMobileNumber(Long.valueOf(studentMap.get("mobileNumber").toString()));
	        student.setParentMobileNumber(Long.valueOf(studentMap.get("parentMobileNumber").toString()));
	        student.setDueDate(LocalDate.parse((String) studentMap.get("dueDate")));

	        // Handle nested address
	        Map<String, Object> addressMap = (Map<String, Object>) studentMap.get("address");
	        Address address = new Address();
	        address.setCity((String) addressMap.get("city"));
	        address.setState((String) addressMap.get("state"));
	        address.setPincode((String) addressMap.get("pincode"));
	        student.setAddress(address);

	        // Decode Base64 images
	        String aadharBase64 = (String) studentMap.get("aadharCard");
	        String panBase64 = (String) studentMap.get("panCard");

	        if (aadharBase64 != null && !aadharBase64.isEmpty()) {
	            student.setAadharCard(Base64.getDecoder().decode(aadharBase64));
	        }
	        if (panBase64 != null && !panBase64.isEmpty()) {
	            student.setPanCard(Base64.getDecoder().decode(panBase64));
	        }

	        Students saved = studentService.insertStudent(student);
	        System.out.println("✅ Student saved: " + saved.getName());
	        return saved;

	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new RuntimeException("❌ Error while saving student", e);
	    }
	}


	
	@GetMapping("/getStudentDashboard")
	public String showDashboard() {
		return "dashboardStudent";
	}
	
	
	
}
