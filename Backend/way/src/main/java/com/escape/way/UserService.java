package com.escape.way;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    public User getUser(Long no){
        return userRepository.findById(no).orElseThrow(() -> 
            new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found user"));
    }

    public List<User> getUserList(){
        return userRepository.findAll();
    }
}