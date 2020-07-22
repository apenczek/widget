package com.penczek.widget;

import com.penczek.widget.filtering.Filtering;

import java.util.List;
import java.util.Optional;

public interface WidgetService {

  Widget create(Widget widget);

  Optional<Widget> update(Widget widget);

  List<Widget> list(Filtering filter);

  void delete(Long id);

  Optional<Widget> get(Long id);

}
