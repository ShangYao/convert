package com.jinanlongen.sparrow.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * 
 * @description
 * @author shangyao
 * @date 2018年4月27日
 */
@Service
public class APiService {
  @Autowired
  @Qualifier("primaryJdbcTemplate")
  JdbcTemplate jdbcTemplate;

  public List<Map<String, Object>> merchandise(String start, String end) {
    String sql =
        "select b.name store_name,b.code store_code,c.code user_code,c.user_name user_name,a.item_id ,a.url from merchandises a,stores b,users c where a.store_id=b.id and a.owner_id=c.id and a.created_at>'"
            + new Date(new Long(start)) + "' and a.created_at<'" + new Date(new Long(end)) + "'";
    List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);

    return list;
  }
}
