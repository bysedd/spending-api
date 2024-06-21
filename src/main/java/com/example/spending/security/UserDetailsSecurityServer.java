package com.example.spending.security;

import com.example.spending.domain.model.User;
import com.example.spending.domain.repository.UserRepository;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserDetailsSecurityServer implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> user = userRepository.findByEmail(username);

    if (user.isEmpty()) {
      throw new UsernameNotFoundException("User or password invalid");
    }

    return user.get();
  }
}
