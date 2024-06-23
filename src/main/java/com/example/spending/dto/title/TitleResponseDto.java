package com.example.spending.dto.title;

import com.example.spending.domain.enums.ETitleType;
import com.example.spending.dto.cost_center.CostCenterResponseDto;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TitleResponseDto {

  private Long id;

  private String description;

  private ETitleType type;

  private List<CostCenterResponseDto> cost_center;

  private double value;

  private Date registerDate;

  private Date referenceDate;

  private Date dueDate;

  private Date paymentDate;

  private String observation;
}
