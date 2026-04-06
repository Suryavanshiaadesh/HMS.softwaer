package com.ss.notifications;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessagesService {

	@Autowired
	MessageRepository messageRepository;
	
	public List<Message> getMessages() {
	    return messageRepository.findAllByOrderByCreatedAtDesc();
	}
	
	
	public String setResolve(Long id) {

        Optional<Message> optionalMsg = messageRepository.findById(id);

        if(optionalMsg.isEmpty()) {
            return "Complaint not found";
        }

        Message msg = optionalMsg.get();

        msg.setStatus(true);

        messageRepository.save(msg);

        return "Complaint Resolved Successfully";
    }
}
