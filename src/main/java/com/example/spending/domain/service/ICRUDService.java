package com.example.spending.domain.service;

import java.util.List;

/**
 * This interface represents a CRUD service provides Create, Read, Update, Delete operations. It is
 * generic and can be used with any data transfer object (Req) for creating and updating records and
 * any response object (Res) for retrieving records.
 *
 * @param <Req> The data transfer object for creating and updating records.
 * @param <Res> The response object for retrieving records.
 */
public interface ICRUDService<Req, Res> {

  Res create(Req dto);

  List<Res> getAll();

  Res getById(Long id);

  Res update(Long id, Req dto);

  void delete(Long id);
}
