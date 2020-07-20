package com.penczek.widget;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
class Paging {

  private Integer page;

  private Integer pageSize;

}
