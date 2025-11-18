package com.ss.student;

import java.time.LocalDate;
import org.hibernate.annotations.CreationTimestamp;

import com.ss.address.Address;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Students {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Long mobileNumber;
    private Long parentMobileNumber; // fixed typo

    @Embedded
    private Address address;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDate dateOfJoining;

    private LocalDate dueDate;

    @Version
    private Long countOfDue; 
    
    @Lob
    private byte[] aadharCard;
    
    @Lob
    private byte[] panCard;
}
