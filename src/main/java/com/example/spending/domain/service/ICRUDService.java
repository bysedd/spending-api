package com.example.spending.domain.service;

import java.util.List;

public interface ICRUDService<Req, Res> {

    List<Res> findAll();

    Res findById(Long id);

    Res register(Req dto);

    Res update(Long id, Req dto);

    void delete(Long id);
}
