package com.penczek.widget.filtering;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Paging {

  private Integer page;

  private Integer pageSize;

}
