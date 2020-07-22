package com.penczek.widget;

import java.util.List;

import com.penczek.widget.Widget;
import com.penczek.widget.WidgetEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WidgetMapper {

  Widget fromEntity(WidgetEntity widgetEntity);

  WidgetEntity toEntity(Widget widget);

  List<Widget> fromEntities(List<WidgetEntity> widgetEntities);

}
