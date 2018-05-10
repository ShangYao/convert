package com.jinanlongen.sparrow;

import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import com.jinanlongen.sparrow.roc.domain.Brand;
import com.jinanlongen.sparrow.roc.repository.BrandRep;

/**
 * 
 * @description
 * @author shangyao
 * @date 2018年5月9日
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class JdbcTest {
  @Autowired
  @Qualifier("rocJdbcTemplate")
  JdbcTemplate jdbcTemplate;
  @Autowired
  BrandRep brandRep;

  @Test
  public void test() {
    List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from users");
    System.out.println(list.size());

  }

  @Test
  public void repTest() {
    List<Brand> list = brandRep.findAll();
    System.out.println(list.size());
    System.out
        .println(list.get(1).getCode() + "--" + list.get(1).getId() + "--" + list.get(1).getName());
  }

}
