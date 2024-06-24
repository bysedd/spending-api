package com.example.spending.controller;

import com.example.spending.domain.service.DashboardService;
import com.example.spending.dto.dashboard.DashboardResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/dashboard")
@AllArgsConstructor
public class DashboardController {

  private DashboardService dashboardService;

  @GetMapping
  public ResponseEntity<DashboardResponseDto> getCashFlow(
      @RequestParam String firstPeriod,
      @RequestParam String finalPeriod
  ) {
    return ResponseEntity.ok(dashboardService.getCashFlow(firstPeriod, finalPeriod));
  }
}
