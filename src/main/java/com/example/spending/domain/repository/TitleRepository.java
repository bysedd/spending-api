package com.example.spending.domain.repository;

import com.example.spending.domain.model.Title;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TitleRepository extends JpaRepository<Title, Long> {}
