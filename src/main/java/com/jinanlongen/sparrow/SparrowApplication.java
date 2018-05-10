package com.jinanlongen.sparrow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import com.jinanlongen.sparrow.service.InitService;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
public class SparrowApplication implements CommandLineRunner {
  @Autowired
  InitService initService;

  public static void main(String[] args) {
    SpringApplication.run(SparrowApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    // TODO Auto-generated method stub
    initService.init();
  }
}
