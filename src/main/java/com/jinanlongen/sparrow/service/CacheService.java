package com.jinanlongen.sparrow.service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.base.Optional;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.jinanlongen.sparrow.roc.domain.CacheKey;
import com.jinanlongen.sparrow.roc.domain.RocData;
import com.jinanlongen.sparrow.roc.repository.BrandRep;
import com.jinanlongen.sparrow.roc.repository.GenderRep;
import com.jinanlongen.sparrow.roc.repository.TaxonRep;

/**
 * 
 * @description
 * @author shangyao
 * @date 2018年5月9日
 */
@Service
public class CacheService {
  private static Logger logger = LoggerFactory.getLogger(CacheService.class);
  @Autowired
  private BrandRep brandRep;
  @Autowired
  private GenderRep genderRep;
  @Autowired
  private TaxonRep taxonRep;
  Cache<CacheKey, List<? extends RocData>> cache =
      CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.HOURS).build();

  @SuppressWarnings("unchecked")
  public List<? extends RocData> getRocDataList(final CacheKey key) {
    List<? extends RocData> returnList = null;
    try {
      returnList = cache.get(key, new Callable<List<? extends RocData>>() {
        @Override
        public List<? extends RocData> call() {
          return getListFromRoc(key);
        }
      });
    } catch (ExecutionException e) {
      logger.error(e.toString());
    }
    return Optional.fromNullable(returnList).or(Collections.EMPTY_LIST);
  }

  private List<? extends RocData> getListFromRoc(CacheKey key) {
    logger.info("获取roc数据：{}", key.name());
    switch (key.name()) {
      case "BRANDS":
        return brandRep.findAll();
      case "TOPTAXONS":
        return taxonRep.findByAncestryIsNull();
      case "GENDERS":
        return genderRep.findAll();

    }
    return null;

  }


}
