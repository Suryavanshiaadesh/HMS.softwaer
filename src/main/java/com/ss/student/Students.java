package com.ss.student;

import java.time.LocalDate;

import com.ss.address.Address;
import com.ss.rooms.Rooms;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Students {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Long mobileNumber;
    private Long parentMobileNumber;

    @Embedded
    private Address address;

    @Column(updatable = false)
    private LocalDate dateOfJoining = LocalDate.now();

    private LocalDate dueDate;

    @Version
    private Long countOfDue;

    @Lob
    private byte[] aadharCard;

    @Lob
    private byte[] panCard;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Rooms room;
}