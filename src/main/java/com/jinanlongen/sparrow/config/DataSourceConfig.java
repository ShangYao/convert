package com.jinanlongen.sparrow.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 
 * @description
 * @author shangyao
 * @date 2018年5月9日
 */
@Configuration
public class DataSourceConfig {
  @Bean(name = "rocDataSource")
  @Qualifier("rocDataSource")
  @ConfigurationProperties(prefix = "spring.datasource.roc")
  public DataSource rocDataSource() {
    return DataSourceBuilder.create().build();
  }

  @Bean(name = "primaryDataSource")
  @Qualifier("primaryDataSource")
  @Primary
  @ConfigurationProperties(prefix = "spring.datasource.primary")
  public DataSource primaryDataSource() {
    return DataSourceBuilder.create().build();
  }



  @Bean(name = "primaryJdbcTemplate")
  public JdbcTemplate primaryJdbcTemplate(@Qualifier("primaryDataSource") DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }

  @Bean(name = "rocJdbcTemplate")
  public JdbcTemplate rocJdbcTemplate(@Qualifier("rocDataSource") DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }



}
