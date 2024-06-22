package com.example.spending.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cost_center")
public class CostCenter {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_cost_center")
  private Long id;

  @Column(nullable = false)
  private String description;

  @Column(columnDefinition = "TEXT")
  private String observation;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToMany(mappedBy = "cost_center")
  @JsonBackReference
  private List<Title> titles;
}
