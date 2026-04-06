package com.ss.student;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ss.rooms.Rooms;

@Repository
public interface StudentRepo extends JpaRepository<Students, Long>{

//	String findByMobileNumber(Object object);
	@Query("SELECT COUNT(s) FROM Students s WHERE s.dueDate < CURRENT_DATE")
	Long countDueStudents();
	
	
	/*
	 * @Query("SELECT s FROM Students s WHERE s.dueDate < CURRENT_DATE")
	 * List<Students> findStudentsWithDue();
	 */
	@Query("SELECT s FROM Students s WHERE s.room IS NOT NULL AND s.dueDate < CURRENT_DATE")
	List<Students> findStudentsWithDue();

	Optional<Students> findByMobileNumber(Long long1);
	
	 @Query("SELECT s.name FROM Students s WHERE s.dueDate < CURRENT_DATE")
	    List<String> findDueStudentNames();
	 
	 
	
	int countByRoom(Rooms room);

}
