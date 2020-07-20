package com.penczek.widget;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WidgetServiceTest {

  @Autowired
  private WidgetService widgetService;

  private Widget getWidgetWithZ() {
    return Widget.builder()
        .x(0)
        .y(0)
        .z(1)
        .width(100.0)
        .height(100.0)
        .build();
  }

  private Widget getWidgetWithoutZ() {
    return Widget.builder()
        .x(0)
        .y(0)
        .width(100.0)
        .height(100.0)
        .build();
  }

  @Test
  void test_create_with_z__success() {
    Widget widget = widgetService.create(getWidgetWithZ());
    Assertions.assertNotNull(widget);
    Assertions.assertNotNull(widget.getId());
    widgetService.delete(widget.getId());
  }


  @Test
  void test_create_without_z__success() {
    Widget widget = widgetService.create(getWidgetWithoutZ());
    Assertions.assertNotNull(widget);
    Assertions.assertNotNull(widget.getId());
    Assertions.assertNotNull(widget.getZ());
    widgetService.delete(widget.getId());
  }

  @Test
  void test_create_repeat_z__success() {
    Widget widget1 = widgetService.create(getWidgetWithZ());
    Assertions.assertNotNull(widget1);
    Assertions.assertNotNull(widget1.getId());
    Assertions.assertEquals(1, widget1.getZ());

    Widget widget2 = widgetService.create(getWidgetWithZ());
    Assertions.assertNotNull(widget2);
    Assertions.assertNotNull(widget2.getId());
    Assertions.assertEquals(1, widget2.getZ());

    Optional<Widget> widget = widgetService.get(widget1.getId());
    Assertions.assertTrue(widget.isPresent());
    Assertions.assertEquals(2, widget.get().getZ());

    widgetService.delete(widget1.getId());
    widgetService.delete(widget2.getId());
  }


  @Test
  void test_update__success() {
    Widget widget = widgetService.create(getWidgetWithZ());
    Assertions.assertNotNull(widget);
    Assertions.assertNotNull(widget.getId());
    Assertions.assertEquals(1, widget.getZ());
    widget.setZ(2);
    widgetService.update(widget).get();

    Optional<Widget> widgetUpdated = widgetService.get(widget.getId());
    Assertions.assertTrue(widgetUpdated.isPresent());
    Assertions.assertEquals(2, widgetUpdated.get().getZ());

    widgetService.delete(widget.getId());
  }


  @Test
  void test_list_with_filter_without_page__success() {

    Widget widget1 = widgetService.create(Widget.builder().x(0).y(0).width(100.0).height(100.0).build());
    Widget widget2 = widgetService.create(Widget.builder().x(0).y(50).width(100.0).height(100.0).build());
    Widget widget3 = widgetService.create(Widget.builder().x(50).y(50).width(100.0).height(100.0).build());

    List<Widget> list1 = widgetService
        .list(Filtering.builder().widgetFiltering(WidgetFiltering.builder().x(0).y(0).width(100.0).height(150.0).build()).build());
    Assertions.assertNotNull(list1);
    Assertions.assertEquals(2, list1.size());

    List<Widget> list2 = widgetService
        .list(Filtering.builder().widgetFiltering(WidgetFiltering.builder().x(0).y(50).width(100.0).height(100.0).build()).build());
    Assertions.assertNotNull(list2);
    Assertions.assertEquals(1, list2.size());

    widgetService.delete(widget1.getId());
    widgetService.delete(widget2.getId());
    widgetService.delete(widget3.getId());


  }


}
