package com.jinanlongen.sparrow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SparrowApplication {

  public static void main(String[] args) {
    SpringApplication.run(SparrowApplication.class, args);
  }


}
