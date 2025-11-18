package com.ss.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
	
	@Autowired
	StudentRepo studentRepo;
	
	public Students insertStudent(Students student) {
		
		return studentRepo.save(student);
	}
}
