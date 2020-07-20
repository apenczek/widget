package com.penczek.widget;

import java.util.Objects;
import javax.validation.Valid;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
class Filtering {

  @Valid
  private WidgetFiltering widgetFiltering;

  private Paging paging;

  boolean hasWidgetFiltering() {
    return !Objects.isNull(widgetFiltering);
  }

}
