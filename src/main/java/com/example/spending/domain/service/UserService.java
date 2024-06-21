package com.example.spending.domain.service;

import com.example.spending.domain.exception.ResourceBadRequestException;
import com.example.spending.domain.exception.ResourceNotFoundException;
import com.example.spending.domain.model.User;
import com.example.spending.domain.repository.UserRepository;
import com.example.spending.dto.user.UserRequestDto;
import com.example.spending.dto.user.UserResponseDto;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements ICRUDService<UserRequestDto, UserResponseDto> {

  private final UserRepository userRepository;

  private final ModelMapper mapper;

  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Override
  public List<UserResponseDto> read() {
    List<User> users = userRepository.findAll();

    return users.stream()
            .map(user -> mapper.map(user, UserResponseDto.class))
            .collect(Collectors.toList());
  }

  @Override
  public UserResponseDto readById(Long id) {
    Optional<User> user = userRepository.findById(id);

    if (user.isEmpty()) {
      throw new ResourceNotFoundException("Unable to find user with id: " + id);
    }

    return mapper.map(user.get(), UserResponseDto.class);
  }

  public UserResponseDto readByEmail(String email) {
    Optional<User> user = userRepository.findByEmail(email);

    if (user.isEmpty()) {
      throw new ResourceNotFoundException("Unable to find user with email: " + email);
    }

    return mapper.map(user.get(), UserResponseDto.class);
  }

  @Override
  public UserResponseDto create(UserRequestDto dto) {
    validateUser(dto);

    Optional<User> optionalUser = userRepository.findByEmail(dto.getEmail());

    if (optionalUser.isPresent()) {
      throw new ResourceBadRequestException(
              "There is already a registered user with the email: " + dto.getEmail());
    }

    User user = mapper.map(dto, User.class);
    user.setId(null);
    user.setRegisterDate(new Date());
    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

    user = userRepository.save(user);
    return mapper.map(user, UserResponseDto.class);
  }

  @Override
  public UserResponseDto update(Long id, UserRequestDto dto) {
    UserResponseDto bankUser = readById(id);
    validateUser(dto);

    User user = mapper.map(dto, User.class);
    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    user.setId(id);
    user.setInactivationDate(bankUser.getInactivationDate());
    user.setRegisterDate(bankUser.getRegisterDate());

    user = userRepository.save(user);
    return mapper.map(user, UserResponseDto.class);
  }

  @Override
  public void delete(Long id) {
    Optional<User> optionalUser = userRepository.findById(id);

    if (optionalUser.isEmpty()) {
      throw new ResourceNotFoundException("Unable to find user with id: " + id);
    }

    User user = optionalUser.get();
    user.setInactivationDate(new Date());

    userRepository.save(user);
  }

  private void validateUser(UserRequestDto dto) {
    if (dto.getEmail() == null || dto.getPassword() == null) {
      throw new ResourceBadRequestException("Email and password are required");
    }
  }
}
