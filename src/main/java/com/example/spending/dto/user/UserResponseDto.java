package com.example.spending.dto.user;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserResponseDto {

    private Long id;

    private String name;

    private String email;

    private String photo;

    private Date inactivationDate;
}
