package com.escape.way.service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import com.escape.way.config.logging.LogEntry;
import com.escape.way.error.CustomException;
import com.escape.way.error.ErrorCode;
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

import static com.escape.way.error.ErrorCode.MEMBER_NOT_FOUND;

//@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @LogEntry(showArgs = true, showResult = true, unit = ChronoUnit.MILLIS)
    public User joinUser(User user){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPw(encoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public User loadUserByUsername(String userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
    }

    public boolean checkIdDuplicate(String id){
        return userRepository.existsByUserId(id);
    }

    public User getUser(Long userNo){
        return userRepository.findById(userNo).orElseThrow(() ->
                new CustomException(MEMBER_NOT_FOUND));
    }

    public User getUserById(String id){
        return userRepository.findByUserId(id)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
    }

    public void updateUser(String id, double latitude, double longitude){
        User u = getUserById(id);
        u.setLatitude(latitude);
        u.setLongitude(longitude);

        userRepository.save(u);
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

    @LogEntry(showArgs = true, showResult = true, unit = ChronoUnit.MILLIS)
    public UserPlace getUserPlace(Long userNo) {
        return userRepository.findPlaceById(userNo)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
    }
}