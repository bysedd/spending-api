package com.example.spending.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/** Represents an error response returned by the server. */
@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {

  private String datetime;

  private Integer status;

  private String title;

  private String message;
}
