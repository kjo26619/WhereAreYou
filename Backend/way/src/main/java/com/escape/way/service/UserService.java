package com.escape.way.service;

import java.util.List;
import java.util.Optional;

import com.escape.way.model.User;
import com.escape.way.repository.UserRepository;
import com.escape.way.vo.UserPlace;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

//@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public User joinUser(User user){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPw(encoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public User loadUserByUsername(String userId) throws UsernameNotFoundException {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException((userId)));
    }

    public boolean checkIdDuplicate(String id){
        return userRepository.existsByUserId(id);
    }

    public User getUser(Long userNo){
        return userRepository.findById(userNo).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found user"));
    }

    public User getUserById(String id){
        return userRepository.findByUserId(id).orElse(null);
    }

    public int updateUser(String id, User user){
        Optional<User> oUser = userRepository.findByUserId(id);
        if(oUser.isPresent()) {
            userRepository.save(oUser.get());
            return 1;
        }
        return 0;
    }

    public int deleteUser(String id) {
        Optional<User> oUser = userRepository.findByUserId(id);
        if (oUser.isPresent()) {
            userRepository.delete(oUser.get());
            return 1;
        }
        return 0;
    }

    public List<User> getUserList(){
        return userRepository.findAll();
    }

    public Optional<User> findByIdPw(String id) { return userRepository.findByUserId(id); }

    public UserPlace getUserPlace(Long userNo) {
        UserPlace result = userRepository.findPlaceById(userNo);

        return result;
    }
}