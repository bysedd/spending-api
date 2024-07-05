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
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * UserService class provides implementation for ICRUDService<UserRequestDto, UserResponseDto>. It
 * allows CRUD operations on User entities.
 */
@Service
@AllArgsConstructor
public class UserService implements ICRUDService<UserRequestDto, UserResponseDto> {

  private UserRepository userRepository;

  private ModelMapper mapper;

  private BCryptPasswordEncoder bCryptPasswordEncoder;

  /**
   * Retrieves a list of all users.
   *
   * @return The list of user response DTOs.
   */
  @Override
  public List<UserResponseDto> getAll() {
    List<User> users = userRepository.findAll();

    return users.stream().map(user -> mapper.map(user, UserResponseDto.class)).toList();
  }

  /**
   * Retrieves a UserResponseDto object by the given id.
   *
   * @param id The ID of the user.
   * @return The UserResponseDto object with the matching ID.
   * @throws ResourceNotFoundException if no user is found with the given ID.
   */
  @Override
  public UserResponseDto getById(Long id) {
    Optional<User> user = userRepository.findById(id);

    if (user.isEmpty()) {
      throw new ResourceNotFoundException("Unable to find user with id: " + id);
    }

    return mapper.map(user.get(), UserResponseDto.class);
  }

  /**
   * Creates a new user.
   *
   * @param dto The UserRequestDto object containing the user details.
   * @return The UserResponseDto object for the created user.
   * @throws ResourceBadRequestException if the email provided in the dto is already registered.
   */
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

  /**
   * Updates a user with the provided ID.
   *
   * @param id The ID of the user to update.
   * @param dto The UserRequestDto object containing the updated user details.
   * @return The UserResponseDto object for the updated user.
   * @throws ResourceBadRequestException if the user is inactivated and cannot be updated.
   * @throws ResourceNotFoundException if no user is found with the given ID.
   */
  @Override
  public UserResponseDto update(Long id, UserRequestDto dto) {
    UserResponseDto bankUser = getById(id);

    if (bankUser.getInactivationDate() != null) {
      throw new ResourceBadRequestException("User inactivated cannot be updated");
    }

    validateUser(dto);
    User user = mapper.map(dto, User.class);

    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    user.setId(id);
    user.setInactivationDate(bankUser.getInactivationDate());
    user.setRegisterDate(bankUser.getRegisterDate());

    user = userRepository.save(user);
    return mapper.map(user, UserResponseDto.class);
  }

  /**
   * Deletes a user by the provided ID.
   *
   * @param id The ID of the user to delete.
   * @throws ResourceNotFoundException if no user is found with the given ID.
   */
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

  /**
   * Validates the UserRequestDto object to ensure that the email and password fields are not null.
   *
   * @param dto The UserRequestDto object to validate.
   * @throws ResourceBadRequestException if the email or password is null in the UserRequestDto
   *     object.
   */
  private void validateUser(UserRequestDto dto) {
    if (dto.getEmail() == null || dto.getPassword() == null) {
      throw new ResourceBadRequestException("Email and password are required");
    }
  }
}
