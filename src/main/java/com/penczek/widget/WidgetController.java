package com.penczek.widget;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/widgets")
@RequiredArgsConstructor
class WidgetController {

  private final WidgetService widgetService;

  @PostMapping
  public ResponseEntity<Widget> create(@Valid @RequestBody Widget widget) {
    return ResponseEntity.ok(widgetService.create(widget));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Widget> update(@Valid @RequestBody Widget widget, @PathVariable("id") Long id) {
    widget.setId(id);
    return ResponseEntity.of(widgetService.update(widget));
  }

  @GetMapping
  public ResponseEntity<List<Widget>> list(@Valid @RequestBody(required = false) Filtering filter) {
    return ResponseEntity.ok(widgetService.list(filter));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Widget> get(@PathVariable("id") Long id) {
    return ResponseEntity.of(widgetService.get(id));
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable("id") Long id) {
    widgetService.delete(id);
  }


}
