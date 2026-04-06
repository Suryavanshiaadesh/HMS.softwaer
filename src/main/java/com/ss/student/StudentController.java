package com.ss.student;

import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ss.address.Address;
import com.ss.email.EmailService;
import com.ss.users.UserRepo;
import com.ss.users.UsersTable;

import jakarta.servlet.http.HttpSession;

@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;
    
    @Autowired
    UserRepo userRepo;
    
    @Autowired
    EmailService emailService;

    @PostMapping("/saveStudentForm")
    @ResponseBody
    public Students saveStudent(@RequestBody Map<String, Object> studentMap) {

        try {
        	String emailId=(String) studentMap.get("emailid");
        	System.out.println(emailId);
            Students student = new Students();
            student.setName((String) studentMap.get("name"));
            student.setMobileNumber(Long.valueOf(studentMap.get("mobileNumber").toString()));
            student.setParentMobileNumber(Long.valueOf(studentMap.get("parentMobileNumber").toString()));

            if(studentMap.get("dueDate") != null && !((String)studentMap.get("dueDate")).isEmpty()) {
                student.setDueDate(LocalDate.parse((String) studentMap.get("dueDate")));
            }

            Map<String, Object> addressMap = (Map<String, Object>) studentMap.get("address");
            if(addressMap != null){
                Address address = new Address();
                address.setCity((String) addressMap.get("city"));
                address.setState((String) addressMap.get("state"));
                address.setPincode((String) addressMap.get("pincode"));
                student.setAddress(address);
            }

            String aadharBase64 = (String) studentMap.get("aadharCard");
            String panBase64 = (String) studentMap.get("panCard");

            if(aadharBase64 != null) student.setAadharCard(Base64.getDecoder().decode(aadharBase64));
            if(panBase64 != null) student.setPanCard(Base64.getDecoder().decode(panBase64));
            
            //check stduent is already exist or not
            if(!studentService.getStudentByPhone(student.getMobileNumber()).isPresent()) {	
            String userName=student.getMobileNumber().toString();
            String[] firstName=student.getName().split(" ");
            String password=firstName[0]+"@123";
            UsersTable user=new UsersTable(userName,password,"student");
            emailService.sendStudentCredentials(emailId,userName,password);
            System.out.println(userRepo.save(user));
            studentService.insertStudent(student);
            
            }
            
            return student;
            
            

        } catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("Error while saving student", e);
        }
    }

    @GetMapping("/getAllStudents")
    @ResponseBody
    public ResponseEntity<List<Students>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }
    @GetMapping("/getStudentDashboard")
    public String getStudentDashboard(HttpSession session,Model model) {
    	UsersTable user=(UsersTable)session.getAttribute("loggedUser");
    	
    	System.out.println(user.toString());
    	model.addAttribute("logged_user",studentService.getStudentByPhone(Long.valueOf(user.getUserName())).get()); 
    	return "dashboardStudent";
    }
    
    @GetMapping("/getStudentCount")
    @ResponseBody
    public Long getAllStudentCount() {
    	return studentService.countAllStudnets();
    }
    
    @GetMapping("/getDueStudentCount")
    @ResponseBody
    public Long getDueStudentCount() {
        return studentService.getDueStudentCount();
    }
    
    @ResponseBody
    @GetMapping("/dueStudents")
    public List<DueStudentDTO> getDueStudents() {
		/*
		 * System.out.println(studentService.getDueStudents() .stream()
		 * .map(DueStudentDTO::new) .collect(Collectors.toList()));
		 */
        return studentService.getDueStudents()
                             .stream()
                             .map(DueStudentDTO::new)
                             .collect(Collectors.toList());
    }
    
    
    @GetMapping("/dueStudentNames")
    @ResponseBody
    public List<String> getDueStudentNames() {
        return studentService.getDueStudentNames();
    }
}