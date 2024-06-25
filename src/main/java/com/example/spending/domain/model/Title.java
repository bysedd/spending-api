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
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a title in the system.
 *
 * <p>A title object contains information such as its ID, description, type, associated user,
 * associated cost centers, value, register date, reference date, due date, payment date, and
 * observation. This class is annotated with JPA annotations to map it to a database table named
 * "title". The class also uses lombok annotations such as @Getter and @Setter to generate getter
 * and setter methods for the fields.
 */
@Entity
@Getter
@Setter
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
      inverseJoinColumns = @JoinColumn(name = "cost_center_id"))
  private List<CostCenter> costCenter;

  @Column(nullable = false)
  private double value;

  private Date registerDate;

  private Date referenceDate;

  private Date dueDate;

  private Date paymentDate;

  @Column(columnDefinition = "TEXT")
  private String observation;
}
