package com.penczek.widget;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WidgetRepository extends JpaRepository<WidgetEntity, Long> {

  List<WidgetEntity> findByZGreaterThanEqualOrderByZDesc(int z);

  Optional<WidgetEntity> findTopByOrderByZDesc();

  Page<WidgetEntity> findAllByOrderByZAsc(Pageable page);

  @Query(value = "SELECT w FROM WidgetEntity w WHERE w.x >= :x AND w.y >= :y AND (w.x + w.width ) <= :width AND ( w.y + w.height ) <= :height ORDER BY w.z ASC")
  Page<WidgetEntity> findByFilterOrderByZAsc(int x, int y, double width, double height, Pageable page);

}
