package com.escape.way.controller;

import com.escape.way.config.RedisUtil;
import com.escape.way.config.TokenUtil;
import com.escape.way.config.logging.LogEntry;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;

@RequestMapping("/api/auth/*")
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

    @RequestMapping(value = "/login", method= RequestMethod.POST)
    @LogEntry(showArgs = true, showResult = true, unit = ChronoUnit.MILLIS)
    public ResponseEntity<?> createAuthenticationToken(UserAuthRequest userInfo) throws Exception {
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

        userService.setUpdateTime(user.getUserNo(), ZonedDateTime.now(ZoneId.of("UTC")));

        return ResponseEntity.ok(new TokenResponse(user.getUserId(), accessToken, refreshToken));
    }

    @ResponseBody
    @RequestMapping(value = "/reAuth", method= RequestMethod.POST)
    @LogEntry(showArgs = true, showResult = true, unit = ChronoUnit.MILLIS)
    public ResponseEntity<?> createRefreshToAccessToken(@RequestBody HashMap<String, String> RefreshToken) throws RuntimeException {
        String refreshToken = RefreshToken.get("RefreshToken");
        String userId = null;

        try {
            userId = redisUtil.getData(refreshToken);

            if (userId.equals(tokenUtil.getUsernameFromToken(refreshToken))) {
                User user = userService.loadUserByUsername(userId);

                String returnAccessToken = tokenUtil.generateAccessToken(user);

                return ResponseEntity.ok(new TokenResponse(user.getUserId(), returnAccessToken, "None"));
            }
            else {
                throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
            }
        }
        catch (Exception e) {
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
