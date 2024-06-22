package com.example.spending.domain.repository;

import com.example.spending.domain.model.CostCenter;
import com.example.spending.domain.model.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CostCenterRepository extends JpaRepository<CostCenter, Long> {

  List<CostCenter> findByUser(User user);
}
