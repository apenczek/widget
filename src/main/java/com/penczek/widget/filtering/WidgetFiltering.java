package com.penczek.widget.filtering;

import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WidgetFiltering {

  @NotNull(message = "x cannot be missing or empty")
  private Integer x;

  @NotNull(message = "y cannot be missing or empty")
  private Integer y;

  @NotNull(message = "width cannot be missing or empty")
  private Double width;

  @NotNull(message = "height cannot be missing or empty")
  private Double height;

}
