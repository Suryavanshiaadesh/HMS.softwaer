package com.ss.notifications;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "msg_seq")
    @SequenceGenerator(name = "msg_seq", sequenceName = "msg_seq", allocationSize = 1)
    private Long id;
    
    private LocalDateTime createdAt;
    private String sender;
    private String content;
    private String studentName;
    private String roomNumber;
    private boolean status;
}