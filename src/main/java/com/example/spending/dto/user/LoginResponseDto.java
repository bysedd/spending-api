package com.example.spending.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto {

  private String token;

  private UserResponseDto user;
}
