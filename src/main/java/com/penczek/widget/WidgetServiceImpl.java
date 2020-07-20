package com.penczek.widget;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
class WidgetServiceImpl implements WidgetService {

  private static final int PAGE_SIZE_DEFAULT = 10;

  private final WidgetRepository widgetRepository;
  private final WidgetMapper widgetMapper;

  @Override
  public Widget create(Widget widget) {
    WidgetEntity widgetEntity = widgetMapper.toEntity(widget);
    if (widgetEntity.hasZ()) {
      List<WidgetEntity> widgetEntities = widgetRepository.findByZGreaterThanEqualOrderByZDesc(widgetEntity.getZ());
      widgetEntities.forEach(w -> w.increaseZ());
      widgetRepository.saveAll(widgetEntities);
    } else {
      widgetEntity.setZ(widgetRepository.findTopByOrderByZDesc().map(WidgetEntity::getZ).orElse(0) + 1);
    }
    return widgetMapper.fromEntity(widgetRepository.save(widgetEntity));
  }

  @Override
  @Transactional
  public Optional<Widget> update(Widget widget) {
    Optional<WidgetEntity> widgetEntity = widgetRepository.findById(widget.getId());
    widgetEntity.ifPresent(we -> we.update(widget));
    return widgetEntity.map(we -> widgetMapper.fromEntity(we));
  }

  @Override
  public List<Widget> list(Filtering filter) {
    List<WidgetEntity> list = Collections.emptyList();
    if (Objects.isNull(filter)) {
      list = widgetRepository.findAllByOrderByZAsc(buildPageable(null)).getContent();
    } else if (filter.hasWidgetFiltering()) {
      list = widgetRepository
          .findByFilterOrderByZAsc(filter.getWidgetFiltering().getX(), filter.getWidgetFiltering().getX(), filter.getWidgetFiltering().getWidth(),
              filter.getWidgetFiltering().getHeight(), buildPageable(filter.getPaging())).getContent();
    } else {
      list = widgetRepository.findAllByOrderByZAsc(buildPageable(filter.getPaging())).getContent();
    }
    return widgetMapper.fromEntities(list);
  }

  @Override
  public void delete(Long id) {
    widgetRepository.deleteById(id);
  }

  @Override
  public Optional<Widget> get(Long id) {
    Optional<WidgetEntity> widgetEntity = widgetRepository.findById(id);
    return widgetEntity.map(we -> widgetMapper.fromEntity(we));
  }

  private Pageable buildPageable(Paging page) {
    if (Objects.isNull(page)) {
      return PageRequest.of(0, PAGE_SIZE_DEFAULT);
    }
    return PageRequest
        .of(Objects.isNull(page.getPage()) ? 0 : page.getPage(), Objects.isNull(page.getPageSize()) ? PAGE_SIZE_DEFAULT : page.getPageSize());
  }

}
