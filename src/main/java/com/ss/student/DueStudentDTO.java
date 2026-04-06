package com.ss.student;

import java.time.LocalDate;

import lombok.Data;

@Data
public class DueStudentDTO {
    private String name;
    private Long mobileNumber;
    private String roomNumber;
    private String roomType;
    private Integer roomCapacity;
    private LocalDate dueDate;

    public DueStudentDTO(Students s) {
        this.name = s.getName();
        this.mobileNumber = s.getMobileNumber();
        if (s.getRoom() != null) {
            this.roomNumber = s.getRoom().getRoomNumber();
            this.roomType = s.getRoom().getType();
            this.roomCapacity = s.getRoom().getCapacity();
        }
        this.dueDate = s.getDueDate();
    }
}