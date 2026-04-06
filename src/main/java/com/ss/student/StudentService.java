package com.ss.student;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
	
	@Autowired
	StudentRepo studentRepo;
	
	public Students insertStudent(Students student) {
		
		return studentRepo.save(student);
	}

	public Optional<Students> getStudentByPhone(Long long1) {
	    return studentRepo.findByMobileNumber(long1);
	}

	public List<Students> getAllStudents() {
		// TODO Auto-generated method stub
		return studentRepo.findAll();
	}

	public Long countAllStudnets() {
		// TODO Auto-generated method stub
		return studentRepo.count();
	}
	
	public Long getDueStudentCount() {
	    return studentRepo.countDueStudents();
	}
	
	public List<String> getDueStudentNames() {
        return studentRepo.findDueStudentNames();
    }
	
	public List<Students> getDueStudents() {
	    return studentRepo.findStudentsWithDue();
	}
	
}
