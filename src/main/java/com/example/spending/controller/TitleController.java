package com.example.spending.controller;

import com.example.spending.domain.service.TitleService;
import com.example.spending.dto.title.TitleRequestDto;
import com.example.spending.dto.title.TitleResponseDto;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/titles")
@AllArgsConstructor
public class TitleController implements ICRUDController<TitleRequestDto, TitleResponseDto> {

  private TitleService titleService;

  @Override
  public ResponseEntity<List<TitleResponseDto>> getAll() {
    return ResponseEntity.ok(titleService.getAll());
  }

  @Override
  public ResponseEntity<List<TitleResponseDto>> getById(Long id) {
    return ResponseEntity.ok(List.of(titleService.getById(id)));
  }

  @Override
  public ResponseEntity<TitleResponseDto> create(TitleRequestDto dto) {
    TitleResponseDto title = titleService.create(dto);
    return new ResponseEntity<>(title, HttpStatus.CREATED);
  }

  @Override
  public ResponseEntity<TitleResponseDto> update(Long id, TitleRequestDto dto) {
    return ResponseEntity.ok(titleService.update(id, dto));
  }

  @Override
  public ResponseEntity<Void> delete(Long id) {
    titleService.delete(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
