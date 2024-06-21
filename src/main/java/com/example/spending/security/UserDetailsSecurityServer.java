package com.example.spending.security;

import com.example.spending.domain.model.User;
import com.example.spending.domain.repository.UserRepository;
import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsSecurityServer implements UserDetailsService {

  public UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) {
    Optional<User> user = userRepository.findByEmail(username);

    if (user.isEmpty()) {
      throw new UsernameNotFoundException("User or password invalid");
    }

    return user.get();
  }
}
