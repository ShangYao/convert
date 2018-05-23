package com.jinanlongen.sparrow;

import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.jinanlongen.sparrow.roc.domain.Brand;
import com.jinanlongen.sparrow.roc.domain.CacheKey;
import com.jinanlongen.sparrow.service.CacheService;

/**
 * 
 * @description
 * @author shangyao
 * @date 2018年5月9日
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CacheTest {
  @Autowired
  CacheService cacheService;

  @SuppressWarnings("unchecked")
  @Test
  public void test() {
    for (int a = 1; a < 5; a++) {
      List<Brand> brands = (List<Brand>) cacheService.getRocDataList(CacheKey.BRANDS);
      System.out.println(brands.size());
    }
  }


}
