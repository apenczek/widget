package com.penczek.filter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Slf4j
public class RateLimitFilter implements javax.servlet.Filter {

  static DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

  private int MAX_REQUESTS_PER_MINUTE = 1000; //or whatever you want it to be

  private int current_minute = 0;

  private int request_count = 0;

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpServletResponse response = (HttpServletResponse) servletResponse;

    if (!StringUtils.isEmpty(request.getParameter("max_request"))) {
      MAX_REQUESTS_PER_MINUTE = Integer.valueOf(request.getParameter("max_request"));
    }

    response.setHeader("X-Rate-Limit", String.valueOf(MAX_REQUESTS_PER_MINUTE));

    LocalDateTime now = LocalDateTime.now();

    if (now.getMinute() != current_minute) {
      current_minute = now.getMinute();
      request_count = 1;
    } else {
      request_count++;
    }

    if (request_count > MAX_REQUESTS_PER_MINUTE) {
      response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
      response.setHeader("X-Rate-Limit-Retry-After", now.plusMinutes(1).withSecond(0).withNano(0).format(formatter));
      response.setHeader("X-Rate-Limit-Remaining", "0");
      return;
    }

    response.setHeader("X-Rate-Limit-Remaining", String.valueOf((MAX_REQUESTS_PER_MINUTE - request_count)));
    filterChain.doFilter(servletRequest, servletResponse);

  }

}
