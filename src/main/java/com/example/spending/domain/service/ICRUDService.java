package com.example.spending.domain.service;

import java.util.List;

public interface ICRUDService<D, R> {

  R create(D dto);

  List<R> getAll();

  R getById(Long id);

  R update(Long id, D dto);

  void delete(Long id);
}
