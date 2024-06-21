package com.example.spending.domain.model;

import static com.example.spending.common.DateConverter.convertDate;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {

  private String datetime;

  private Integer status;

  private String title;

  private String message;

  public void setDatetime(Date datetime) {
    this.datetime = convertDate(datetime);
  }
}
