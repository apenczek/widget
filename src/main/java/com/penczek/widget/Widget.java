package com.penczek.widget;

import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
class Widget {

  private Long id;

  @NotNull(message = "x cannot be missing or empty")
  private Integer x;

  @NotNull(message = "y cannot be missing or empty")
  private Integer y;

  @NotNull(message = "z cannot be missing or empty")
  private Integer z;

  @NotNull(message = "width cannot be missing or empty")
  private Double width;

  @NotNull(message = "height cannot be missing or empty")
  private Double height;

  private LocalDateTime lastUpdate;


}
