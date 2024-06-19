package com.example.spending.domain.service;

import com.example.spending.dto.user.UserRequestDto;
import com.example.spending.dto.user.UserResponseDto;

import java.util.List;

public class UserService implements ICRUDService<UserRequestDto, UserResponseDto> {

    @Override
    public List<UserResponseDto> findAll() {
        return List.of();
    }

    @Override
    public UserResponseDto findById(Long id) {
        return null;
    }

    @Override
    public UserResponseDto register(UserRequestDto dto) {
        return null;
    }

    @Override
    public UserResponseDto update(Long id, UserRequestDto dto) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
