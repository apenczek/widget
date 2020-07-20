package com.penczek.widget;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class WidgetApplication {

  public static void main(String[] args) {
    SpringApplication.run(WidgetApplication.class, args);
  }

}
