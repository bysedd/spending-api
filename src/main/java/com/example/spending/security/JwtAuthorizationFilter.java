package com.example.spending.security;

import com.example.spending.domain.model.User;
import com.example.spending.domain.service.UserService;
import com.example.spending.dto.user.UserResponseDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final JwtUtil jwtUtil;

    private final UserService userService;

    private ModelMapper mapper;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserService userService) {
        super(authenticationManager);
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) return;

        String token = header.substring(7);
        UsernamePasswordAuthenticationToken auth = getAuthentication(token);

        if (auth != null && auth.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        if (!jwtUtil.validateToken(token)) return null;

        String email = jwtUtil.getUsername(token);
        UserResponseDto userDto = userService.readByEmail(email);
        User user = mapper.map(userDto, User.class);

        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }
}
























