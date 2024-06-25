package com.example.spending.domain.service;

import com.example.spending.domain.enums.ETitleType;
import com.example.spending.dto.dashboard.DashboardResponseDto;
import com.example.spending.dto.title.TitleResponseDto;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/** Service class for handling dashboard-related operations. */
@Service
@AllArgsConstructor
public class DashboardService {

  private TitleService titleService;

  /**
   * Method to get the cash flow between two periods.
   *
   * @param firstPeriod The start of the period.
   * @param finalPeriod The end of the period.
   * @return DashboardResponseDto The response object containing the cash flow details.
   */
  public DashboardResponseDto getCashFlow(String firstPeriod, String finalPeriod) {
    // Get the titles by due date
    List<TitleResponseDto> titles = titleService.getByDueDate(firstPeriod, finalPeriod);

    // Initialize lists for payable and receivable titles
    List<TitleResponseDto> titlesPayable = new ArrayList<>();
    List<TitleResponseDto> titlesReceivable = new ArrayList<>();
    // Initialize total payable and receivable amounts
    double totalPayable = 0;
    double totalReceivable = 0;

    // Iterate over the titles
    for (TitleResponseDto title : titles) {
      // If the title is payable, add its value to total payable and add it to payable titles list
      if (title.getType().equals(ETitleType.PAYABLE)) {
        totalPayable += title.getValue();
        titlesPayable.add(title);
      } else {
        // If the title is receivable, add its value to total receivable and add it to receivable
        // titles list
        totalReceivable += title.getValue();
        titlesReceivable.add(title);
      }
    }
    // Calculate the balance
    double balance = totalReceivable - totalPayable;

    // Return the response object with the calculated values
    return new DashboardResponseDto(
        totalReceivable, totalPayable, balance, titlesReceivable, titlesPayable);
  }
}
