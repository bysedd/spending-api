package com.example.spending.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

import static com.example.spending.common.DateConverter.convertDate;

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
