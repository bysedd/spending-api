package com.example.spending.domain.model;

import com.example.spending.domain.enums.ETitleType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import java.util.Date;
import java.util.List;

@Entity
public class Title {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "title_id")
  private Long id;

  @Column(nullable = false)
  private String description;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  private ETitleType type;

  @ManyToMany
  @JoinTable(
      name = "title_cost_center",
      joinColumns = @JoinColumn(name = "title_id"),
      inverseJoinColumns = @JoinColumn(name = "cost_center_id")
  )
  private List<CostCenter> cost_center;

  @Column(nullable = false)
  private double value;

  private Date registerDate;

  private Date referenceDate;

  private Date dueDate;

  private Date paymentDate;

  @Column(columnDefinition = "TEXT")
  private String observation;
}
