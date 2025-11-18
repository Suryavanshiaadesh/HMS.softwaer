package com.ss.users;

import java.util.Optional;

import com.ss.student.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

   
	@Autowired
	UserRepo userRepo;

    
	
	public Optional<UsersTable> CheckUser(String userName ,String passWord){
		return userRepo.findByUserNameAndPassWord(userName, passWord);	
	}

	public UsersTable saveUser(UsersTable user) {
		return userRepo.save(user);
		
	}
	
	
}
