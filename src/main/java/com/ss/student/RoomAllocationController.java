package com.ss.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ss.rooms.Rooms;
import com.ss.rooms.RoomsRepository;

@RestController
public class RoomAllocationController {

    @Autowired
    private StudentRepo studentRepository;

    @Autowired
    private RoomsRepository roomsRepository;

    @PostMapping("/allocateRoom")
    public String allocateRoom(@RequestParam Long studentId,
                               @RequestParam Long roomId) {

        Students student = studentRepository
                .findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Rooms room = roomsRepository
                .findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        int capacity = room.getCapacity();
        int occupied = room.getOccupied() == null ? 0 : room.getOccupied();

        if (occupied >= capacity) {
            return "Room is already full";
        }

        room.setOccupied(occupied + 1);

        student.setRoom(room);

        roomsRepository.save(room);
        studentRepository.save(student);

        return "Room allocated successfully";
    }
}