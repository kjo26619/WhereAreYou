package com.escape.way.controller;

import com.escape.way.config.RedisUtil;
import com.escape.way.config.TokenUtil;
import com.escape.way.dto.TokenResponse;
import com.escape.way.model.User;
import com.escape.way.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping(value = "/api/auth", method= RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestParam String userId, @RequestParam String pw) throws Exception {
        System.out.println("Request create access token : " + userId + " " + pw);

        authenticate(userId, pw);

        final User user = userService.loadUserByUsername(userId);

        final String accessToken = tokenUtil.generateAccessToken(user);

        return ResponseEntity.ok(new TokenResponse(user.getName(), accessToken));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        }
        catch(DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        }
        catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
