package com.example.spending.domain.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Title {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToMany
    private List<CostCenter> cost_center;
}
