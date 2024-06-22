package com.example.spending.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ETitleType {
  RECEIVABLE("Receivable"),
  PAYABLE("Payable");

  private final String value;
}
