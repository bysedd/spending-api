package com.example.spending.controller;

import com.example.spending.domain.service.UserService;
import com.example.spending.dto.user.UserRequestDto;
import com.example.spending.dto.user.UserResponseDto;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping
  public ResponseEntity<List<UserResponseDto>> read() {
    return ResponseEntity.ok(userService.read());
  }

  @GetMapping("/{id}")
  public ResponseEntity<List<UserResponseDto>> readById(@PathVariable Long id) {
    return ResponseEntity.ok(Collections.singletonList(userService.readById(id)));
  }

  @PostMapping
  public ResponseEntity<UserResponseDto> create(@RequestBody UserRequestDto dto) {
    UserResponseDto user = userService.create(dto);
    return new ResponseEntity<>(user, HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<UserResponseDto> update(@PathVariable Long id,
          @RequestBody UserRequestDto dto) {
    UserResponseDto user = userService.update(id, dto);
    return new ResponseEntity<>(user, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    userService.delete(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
