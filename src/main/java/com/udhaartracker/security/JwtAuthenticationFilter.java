package com.udhaartracker.security;

import com.udhaartracker.emun.ErrorCode;
import com.udhaartracker.entity.User;
import com.udhaartracker.repository.UserRepository;
import com.udhaartracker.utils.ResponseUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private ResponseUtils responseUtils;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = authorizationHeader.substring(7);


        if(!jwtService.validateToken(token)) {
            responseUtils.writeErrorResponse(response, HttpStatus.UNAUTHORIZED,"Token is not valid", ErrorCode.UNAUTHORIZED);
            return;
        }

        String email = jwtService.extractEmailFromToken(token);

        User user = userRepository.findByEmail(email);

        if(user!=null){
            Integer tokenVersion = jwtService.extractTokenVersionFromToken(token);

            if(!user.getTokenVersion().equals(tokenVersion)){
                filterChain.doFilter(request, response);
                return;
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email,null, Collections.emptyList());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);


    }
}
