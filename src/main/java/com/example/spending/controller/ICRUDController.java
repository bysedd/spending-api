package com.example.spending.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface ICRUDController<D, R> {

  @GetMapping
  ResponseEntity<List<R>> getAll();

  @GetMapping("/{id}")
  ResponseEntity<R> getById(@PathVariable Long id);

  @PostMapping
  ResponseEntity<R> create(@RequestBody D dto);

  @PutMapping("/{id}")
  ResponseEntity<R> update(@PathVariable Long id, @RequestBody D dto);

  @DeleteMapping("/{id}")
  ResponseEntity<Void> delete(@PathVariable Long id);
}
