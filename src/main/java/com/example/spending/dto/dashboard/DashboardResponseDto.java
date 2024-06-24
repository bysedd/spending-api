package com.example.spending.dto.dashboard;

import com.example.spending.dto.title.TitleResponseDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DashboardResponseDto {

  private double totalReceivable;

  private double totalPayable;

  private double balance;

  private List<TitleResponseDto> titlesReceivable;

  private List<TitleResponseDto> titlesPayable;
}
