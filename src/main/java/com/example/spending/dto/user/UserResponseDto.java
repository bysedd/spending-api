package com.example.spending.dto.user;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {

  private Long id;

  private String name;

  private String email;

  private String photo;

  private Date registerDate;

  private Date inactivationDate;
}
