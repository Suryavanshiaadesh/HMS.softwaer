package com.ss.users;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<UsersTable, String>{
	
	public Optional<UsersTable> findByUserNameAndPassWord(String userName, String passWord);

}
