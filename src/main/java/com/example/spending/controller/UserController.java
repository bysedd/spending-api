package com.example.spending.controller;

import com.example.spending.domain.service.UserService;
import com.example.spending.dto.user.UserRequestDto;
import com.example.spending.dto.user.UserResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<UserResponseDto>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(Collections.singletonList(userService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> register(@RequestBody UserRequestDto dto) {
        UserResponseDto user = userService.register(dto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> update(@PathVariable Long id, @RequestBody UserRequestDto dto) {
        UserResponseDto user = userService.update(id, dto);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
