package com.example.spending.controller;

import com.example.spending.domain.service.CostCenterService;
import com.example.spending.dto.custcenter.CostCenterRequestDto;
import com.example.spending.dto.custcenter.CostCenterResponseDto;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/cost-center")
@AllArgsConstructor
public class CostCenterController
    implements ICRUDController<CostCenterRequestDto, CostCenterResponseDto> {

  private CostCenterService costCenterService;

  @Override
  public ResponseEntity<List<CostCenterResponseDto>> getAll() {
    return ResponseEntity.ok(costCenterService.getAll());
  }

  @Override
  public ResponseEntity<List<CostCenterResponseDto>> getById(Long id) {
    return ResponseEntity.ok(List.of(costCenterService.getById(id)));
  }

  @Override
  public ResponseEntity<CostCenterResponseDto> create(CostCenterRequestDto dto) {
    CostCenterResponseDto costCenter = costCenterService.create(dto);
    return new ResponseEntity<>(costCenter, HttpStatus.CREATED);
  }

  @Override
  public ResponseEntity<CostCenterResponseDto> update(Long id, CostCenterRequestDto dto) {
    return ResponseEntity.ok(costCenterService.update(id, dto));
  }

  @Override
  public ResponseEntity<?> delete(Long id) {
    costCenterService.delete(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
