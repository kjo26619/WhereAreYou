package com.escape.way.controller;

import com.escape.way.config.RedisUtil;
import com.escape.way.config.TokenUtil;
import com.escape.way.dto.TokenResponse;
import com.escape.way.dto.UserAuthRequest;
import com.escape.way.error.CustomException;
import com.escape.way.error.ErrorCode;
import com.escape.way.error.ErrorResponse;
import com.escape.way.model.User;
import com.escape.way.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
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
    public ResponseEntity<?> createAuthenticationToken(UserAuthRequest userInfo) throws RuntimeException {
        String userId = userInfo.getUserId();
        String password = userInfo.getPassword();

        try {
            authenticate(userId, password);
        }
        catch(Exception e) {
            throw new CustomException(ErrorCode.LOGIN_FAILED);
        }

        final User user = userService.loadUserByUsername(userId);

        final String accessToken = tokenUtil.generateAccessToken(user);
        final String refreshToken = tokenUtil.generateRefreshToken(user);

        redisUtil.setDataExpire(refreshToken, user.getUserId(), tokenUtil.REFRESH_TOKEN_VALIDATION);

        return ResponseEntity.ok(new TokenResponse(user.getUserId(), accessToken, refreshToken));
    }

    @RequestMapping(value = "/api/reAuth", method= RequestMethod.POST)
    public ResponseEntity<?> createRefreshToAccessToken(@RequestParam String RefreshToken) throws RuntimeException {

        String userId = null;

        userId = redisUtil.getData(RefreshToken);

        if (userId == null) {
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        if (userId.equals(tokenUtil.getUsernameFromToken(RefreshToken))) {
            User user = userService.loadUserByUsername(userId);

            String returnAccessToken = tokenUtil.generateAccessToken(user);

            return ResponseEntity.ok(new TokenResponse(user.getUserId(), returnAccessToken, "None"));
        }
        else {
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
        }
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
