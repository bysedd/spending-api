package com.example.spending.domain.service;

import java.util.List;

public interface ICRUDService<Req, Res> {

    Res create(Req dto);

    List<Res> read();

    Res readById(Long id);

    Res update(Long id, Req dto);

    void delete(Long id);
}
