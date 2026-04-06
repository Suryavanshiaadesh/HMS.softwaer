package com.ss.rooms;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ss.student.StudentRepo;

@Service
public class RoomsService {
	@Autowired
	RoomsRepository room_repo;
	
	@Autowired
	StudentRepo studentRepo;
	
	
	
	public List<Rooms> getVacantRooms() {
	    List<Rooms> rooms = room_repo.findAll();
	    List<Rooms> vacantRooms = new ArrayList<Rooms>();

	    for (Rooms room : rooms) {
	        int studentCount = studentRepo.countByRoom(room);
	        int capacity = room.getCapacity();

	        if (studentCount < capacity) {
	            vacantRooms.add(room);
	        }
	    }

	    return vacantRooms;
	}
	
	public Long totalRooms() {
		return room_repo.count();
	}

	public Long vacontCount() {
		
		return (long) getVacantRooms().size();
	}
}
