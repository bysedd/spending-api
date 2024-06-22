package com.example.spending.dto.custcenter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CostCenterRequestDto {

  private Long id;

  private String description;

  private String observation;
}
