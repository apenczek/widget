package com.penczek.widget;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@ExtendWith(SpringExtension.class)
@WebMvcTest(value = WidgetController.class, excludeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE, classes = RateLimitFilter.class))
public class WidgetControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private WidgetService widgetService;

  private Widget getWidget() {
    return Widget.builder()
        .id(1L)
        .x(0)
        .y(0)
        .z(0)
        .width(100.0)
        .height(100.0)
        .lastUpdate(LocalDateTime.now())
        .build();
  }

  private Widget getWidgetAsParameter() {
    return Widget.builder()
        .x(0)
        .y(0)
        .z(0)
        .width(100.0)
        .height(100.0)
        .build();
  }

  private String getJSON(Widget widget) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
    return ow.writeValueAsString(widget);

  }

  @Test
  public void test_get__success() throws Exception {
    when(widgetService.get(any())).thenReturn(Optional.of(getWidget()));
    mvc.perform(MockMvcRequestBuilders.get("/widgets/1"))
        .andExpect(status().isOk());
  }

  @Test
  public void test_get__not_found() throws Exception {
    when(widgetService.get(any())).thenReturn(Optional.empty());
    mvc.perform(MockMvcRequestBuilders.get("/widgets/1"))
        .andExpect(status().isNotFound());
  }

  @Test
  public void test_create__success() throws Exception {
    when(widgetService.create(any())).thenReturn(getWidget());
    mvc.perform(MockMvcRequestBuilders.post("/widgets").contentType(MediaType.APPLICATION_JSON).content(getJSON(getWidgetAsParameter())))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", CoreMatchers.is(1)));
  }

  @Test
  public void test_update__not_found() throws Exception {
    when(widgetService.update(any())).thenReturn(Optional.empty());
    mvc.perform(MockMvcRequestBuilders.put("/widgets/1").contentType(MediaType.APPLICATION_JSON).content(getJSON(getWidgetAsParameter())))
        .andExpect(status().isNotFound());
  }

  @Test
  public void test_update__success() throws Exception {
    when(widgetService.update(any())).thenReturn(Optional.of(getWidget()));
    mvc.perform(MockMvcRequestBuilders.put("/widgets/1").contentType(MediaType.APPLICATION_JSON).content(getJSON(getWidgetAsParameter())))
        .andExpect(status().isOk());
  }

  @Test
  public void test_list__success() throws Exception {
    when(widgetService.list(any())).thenReturn(Arrays.asList(getWidget()));
    mvc.perform(MockMvcRequestBuilders.get("/widgets"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", Matchers.hasSize(1)));
  }

  @Test
  public void test_list_size_2__success() throws Exception {
    when(widgetService.list(any())).thenReturn(Arrays.asList(getWidget(), getWidget()));
    mvc.perform(MockMvcRequestBuilders.get("/widgets"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", Matchers.hasSize(2)));
  }

}
