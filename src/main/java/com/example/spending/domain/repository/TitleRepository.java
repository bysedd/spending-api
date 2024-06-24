package com.example.spending.domain.repository;

import com.example.spending.domain.model.Title;
import com.example.spending.domain.model.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TitleRepository extends JpaRepository<Title, Long> {

  @Query(
      nativeQuery = true,
      value =
          """
          SELECT *
          FROM public.title
          WHERE due_date
                    BETWEEN to_timestamp(:firstPeriod, 'YYYY-MM-DD HH24:MI:SS')
                    AND to_timestamp(:finalPeriod, 'YYYY-MM-DD HH24:MI:SS');
                   \s""")
  List<Title> getCashFlowByDueDate(
      @Param("firstPeriod") String firstPeriod, @Param("finalPeriod") String finalPeriod);

  List<Title> findByUser(User user);
}
