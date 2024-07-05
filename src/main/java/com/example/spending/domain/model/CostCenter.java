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

/**
 * Represents a cost center in the system.
 *
 * <p>
 * A cost center object contains information such as its ID, description, observation, associated
 * user, and associated titles. This class is annotated with JPA annotations to map it to a database
 * table named "cost_center". The class also uses Lombok annotations such as @Getter and @Setter to
 * generate getter and setter methods for the fields.
 * </p>
 */
@Getter
@Setter
@Entity
@Table(name = "cost_center")
public class CostCenter {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "cost_center_id")
  private Long id;

  @Column(nullable = false)
  private String description;

  @Column(columnDefinition = "TEXT")
  private String observation;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToMany(mappedBy = "costCenter")
  @JsonBackReference
  private List<Title> titles;
}
