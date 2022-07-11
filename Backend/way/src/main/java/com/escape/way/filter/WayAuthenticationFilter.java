package com.escape.way.filter;

import com.escape.way.config.RedisUtil;
import com.escape.way.config.TokenUtil;
import com.escape.way.model.User;
import com.escape.way.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@Component
public class WayAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Enumeration<String> params = request.getParameterNames();
        String accessToken = null;
        String refreshToken = null;

        String userId = null;


        while(params.hasMoreElements()) {
            String key = params.nextElement();
            if(key.equals("AccessToken")) {
                accessToken = request.getParameter(key);
                System.out.println(key);
                System.out.println(accessToken);
            }
            else if(key.equals("RefreshToken")) {
                refreshToken = request.getParameter(key);
            }
        }

        try {
            if (accessToken != null) {
                System.out.println(accessToken);
                userId = tokenUtil.getUsernameFromToken(accessToken);
                System.out.println("userId : " + userId);
            }

            if (userId != null) {
                User user = userService.loadUserByUsername(userId);

                if(tokenUtil.validateToken(accessToken, user)) {
                    UsernamePasswordAuthenticationToken upaToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    upaToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(upaToken);
                }
            }
        }catch(ExpiredJwtException e) {
            System.out.println(e.toString());
        }

        /*
        try {
            if (refreshToken != null) {
                userId = redisUtil.getData(refreshToken);

                if (userId.equals(tokenUtil.getUsernameFromToken(refreshToken))) {
                    User user = userService.loadUserByUsername(userId);

                    UsernamePasswordAuthenticationToken upaToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    upaToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(upaToken);

                    String returnAccessToken = tokenUtil.generateAccessToken(user);

                    request.setAttribute("AccessToken", returnAccessToken);
                }
            }
        }catch(ExpiredJwtException e) {
            e.getMessage();
        }
        */
        filterChain.doFilter(request, response);
    }
}
