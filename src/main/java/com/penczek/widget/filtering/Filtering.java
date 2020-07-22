package com.penczek.widget.filtering;

import java.util.Objects;
import javax.validation.Valid;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Filtering {

  @Valid
  private WidgetFiltering widgetFiltering;

  private Paging paging;

  public boolean hasWidgetFiltering() {
    return !Objects.isNull(widgetFiltering);
  }

}
