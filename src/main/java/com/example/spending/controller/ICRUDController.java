package com.example.spending.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface ICRUDController<Req, Res> {

  @GetMapping
  ResponseEntity<List<Res>> getAll();

  @GetMapping("/{id}")
  ResponseEntity<List<Res>> getById(@PathVariable Long id);

  @PostMapping
  ResponseEntity<Res> create(@RequestBody Req dto);

  @PutMapping("/{id}")
  ResponseEntity<Res> update(@PathVariable Long id, @RequestBody Req dto);

  @DeleteMapping("/{id}")
  ResponseEntity<?> delete(@PathVariable Long id);
}
