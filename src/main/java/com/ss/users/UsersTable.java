package com.ss.users;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersTable {
	@Id
	private String userName;
	private String passWord;
	
	private String roll="Student";
	

}
