package com.penczek.widget;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@ExtendWith(SpringExtension.class)
@WebMvcTest(WidgetController.class)
public class WidgetRateLimitTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private WidgetService widgetService;

  @Test
  public void test_too_many_request__success() throws Exception {
    when(widgetService.list(any())).thenReturn(Collections.emptyList());

    mvc.perform(MockMvcRequestBuilders.get("/widgets").param("max_request", "2"))
        .andExpect(status().isOk());

    mvc.perform(MockMvcRequestBuilders.get("/widgets"))
        .andExpect(status().isOk());

    mvc.perform(MockMvcRequestBuilders.get("/widgets"))
        .andExpect(status().isTooManyRequests());

  }

}
