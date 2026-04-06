package com.ss;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import java.time.LocalDateTime;
import com.ss.notifications.Message;
import com.ss.notifications.MessageRepository;
import com.ss.student.StudentService;
import com.ss.student.Students;
import com.ss.users.UsersTable;

import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

@Controller
public class MessageController {
	
	@Autowired
	StudentService studentService;
	
	@Autowired
	private MessageRepository messageRepository;



	@MessageMapping("/send")
	@SendTo("/topic/messages")
	public Message sendMessage(Message message, SimpMessageHeaderAccessor headerAccessor) {

	    System.out.println("Received: " + message.getContent());
	    System.out.println("WS SESSION: " + headerAccessor.getSessionAttributes());
	    UsersTable user = (UsersTable) headerAccessor.getSessionAttributes().get("loggedUser");
System.out.println("============================================================="+user.toString());
	    if (user != null) {
	    	
	        Students student = studentService
	                .getStudentByPhone(Long.valueOf(user.getUserName()))
	                .orElse(null);

	        if (student != null) {
	        	System.out.println("student is "+student.toString());
	            message.setStudentName(student.getName());

	            if (student.getRoom() != null) {
	                message.setRoomNumber(student.getRoom().getRoomNumber());
	            }
	        }
	    }

	    message.setCreatedAt(LocalDateTime.now());

	    // save
	    Message savedMessage = messageRepository.save(message);

	    return savedMessage; 
	}
}