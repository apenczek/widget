package com.penczek.widget;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.BeanUtils;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class WidgetEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "x")
  private Integer x;

  @Column(name = "y")
  private Integer y;

  @Column(name = "z", unique = true)
  private Integer z;

  private Double width;

  private Double height;

  @Column(name = "last_update")
  @UpdateTimestamp
  private LocalDateTime lastUpdate;

  boolean hasZ() {
    return !Objects.isNull(z);
  }

  void increaseZ() {
    z++;
  }

  void update(Widget widget) {
    BeanUtils.copyProperties(widget, this, "id", "lastUpdate");
    lastUpdate = LocalDateTime.now();
  }

}
