package com.example.spending.security;

import com.example.spending.domain.model.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

  private final JwtUtil jwtUtil;

  private final UserDetailsSecurityServer userDetailsSecurityServer;

  public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil,
      UserDetailsSecurityServer userDetailsSecurityServer) {
    super(authenticationManager);
    this.jwtUtil = jwtUtil;
    this.userDetailsSecurityServer = userDetailsSecurityServer;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain) throws IOException, ServletException {
    String header = request.getHeader("Authorization");

    if (header != null && header.startsWith("Bearer ")) {
      UsernamePasswordAuthenticationToken auth = getAuthentication(header.substring(7));

      if (auth != null && auth.isAuthenticated()) {
        SecurityContextHolder.getContext().setAuthentication(auth);
      }
    }

    chain.doFilter(request, response);
  }

  private UsernamePasswordAuthenticationToken getAuthentication(String token) {
    if (jwtUtil.validateToken(token)) {

      String email = jwtUtil.getUsername(token);

      User user = (User) userDetailsSecurityServer.loadUserByUsername(email);

      return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }

    return null;
  }
}
