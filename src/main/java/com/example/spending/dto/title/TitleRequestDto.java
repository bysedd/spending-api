package com.example.spending.dto.title;

import com.example.spending.domain.enums.ETitleType;
import com.example.spending.dto.cost_center.CostCenterRequestDto;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TitleRequestDto {

  private Long id;

  private String description;

  private ETitleType type;

  private List<CostCenterRequestDto> cost_center;

  private double value;

  private Date registerDate;

  private Date referenceDate;

  private Date dueDate;

  private Date paymentDate;

  private String observation;
}
