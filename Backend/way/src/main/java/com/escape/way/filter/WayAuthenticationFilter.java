package com.escape.way.filter;

import com.escape.way.config.RedisUtil;
import com.escape.way.config.TokenUtil;
import com.escape.way.model.User;
import com.escape.way.service.UserService;
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
        String accessTokenPrefix = request.getHeader("Authorization");

        System.out.println("Bearer AccessToken : " + accessTokenPrefix);

        if (accessTokenPrefix == null || !accessTokenPrefix.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = accessTokenPrefix.replace("Bearer ", "");
        String userId = null;

        try {
            if (accessToken != null || !accessToken.isEmpty()) {
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
        }catch(Exception e) {
            System.out.println(e.toString());
        }

        filterChain.doFilter(request, response);
    }
}
