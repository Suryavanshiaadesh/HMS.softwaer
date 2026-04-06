package com.ss.notifications;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessagesController {
	
	@Autowired
	MessagesService messageService;
	
	@GetMapping("/messages")
	public ResponseEntity<List<Message>> getMessages() {

	    List<Message> messages = messageService.getMessages();
	    if (messages.isEmpty()) {
	        return ResponseEntity.noContent().build();
	    }
	    System.out.println("list"+messages);
	    return ResponseEntity.ok(messages);
	}
	
	@PostMapping("/setResolve/{id}")
    public ResponseEntity<String> setResolve(@PathVariable Long id){

        String response = messageService.setResolve(id);

        return ResponseEntity.ok(response);
    }
}
