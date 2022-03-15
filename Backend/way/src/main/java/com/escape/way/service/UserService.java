package com.escape.way.service;

import java.util.List;
import java.util.Optional;

import com.escape.way.model.User;
import com.escape.way.repository.UserRepository;
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

    public User joinUser(User user){
        Long no = Long.parseLong(user.getId());
        Optional<User> newUser = userRepository.findById(no);
        return userRepository.save(newUser.orElse(null));
    }

    public User updateUser(User user){
        //Todo
        return user;
    }

    public void deleteUser(User user){
       userRepository.delete(user);
    }

    public List<User> getUserList(){
        return userRepository.findAll();
    }
}