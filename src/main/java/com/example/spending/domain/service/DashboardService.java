package com.example.spending.domain.service;

import com.example.spending.domain.enums.ETitleType;
import com.example.spending.dto.dashboard.DashboardResponseDto;
import com.example.spending.dto.title.TitleResponseDto;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DashboardService {

  private TitleService titleService;

  public DashboardResponseDto getCashFlow(String firstPeriod, String finalPeriod) {
    List<TitleResponseDto> titles = titleService.getByDueDate(firstPeriod, finalPeriod);

    List<TitleResponseDto> titlesPayable = new ArrayList<>();
    List<TitleResponseDto> titlesReceivable = new ArrayList<>();
    double totalPayable = 0;
    double totalReceivable = 0;

    for (TitleResponseDto title : titles) {
      if (title.getType().equals(ETitleType.PAYABLE)) {
        totalPayable += title.getValue();
        titlesPayable.add(title);
      } else {
        totalReceivable += title.getValue();
        titlesReceivable.add(title);
      }
    }
    double balance = totalReceivable - totalPayable;

    return new DashboardResponseDto(
        totalReceivable,
        totalPayable,
        balance,
        titlesReceivable,
        titlesPayable
    );
  }
}
