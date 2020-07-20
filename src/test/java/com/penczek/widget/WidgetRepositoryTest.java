package com.penczek.widget;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class WidgetRepositoryTest {

  @Autowired
  private WidgetRepository widgetRepository;

  @Test
  public void test_find_filter__success() {
    WidgetEntity widget0_0_100_100 = WidgetEntity.builder().x(0).y(0).width(100.0).height(100.0).build();
    WidgetEntity widget0_50_100_100 = WidgetEntity.builder().x(0).y(50).width(100.0).height(100.0).build();
    WidgetEntity widget50_50_100_100 = WidgetEntity.builder().x(50).y(50).width(100.0).height(100.0).build();

    List<WidgetEntity> list = widgetRepository.saveAll(Arrays.asList(widget0_0_100_100, widget0_50_100_100, widget50_50_100_100));

    Page<WidgetEntity> page = widgetRepository.findByFilterOrderByZAsc(0, 0, 100, 150, PageRequest.of(0, 10));
    Assertions.assertNotNull(page);
    Assertions.assertEquals(2, page.getTotalElements());

    Page<WidgetEntity> page5050 = widgetRepository.findByFilterOrderByZAsc(50, 50, 150, 150, PageRequest.of(0, 10));
    Assertions.assertNotNull(page5050);
    Assertions.assertEquals(1, page5050.getTotalElements());

    widgetRepository.deleteAll(list);
  }


}
