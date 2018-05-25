package com.jinanlongen.sparrow.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.jinanlongen.sparrow.domain.EsMerchandise;
import com.jinanlongen.sparrow.domain.Merchandise;
import com.jinanlongen.sparrow.result.RefinedData;
import com.jinanlongen.sparrow.result.Result;
import com.jinanlongen.sparrow.result.ResultGenerator;
import com.jinanlongen.sparrow.service.APiService;
import com.jinanlongen.sparrow.service.RefinedMineService;

/**
 * 
 * @description
 * @author shangyao
 * @date 2018年3月21日
 */
@RestController
@RequestMapping("/api/")
public class ApiController {
  @Autowired
  APiService apiService;
  @Autowired
  private RefinedMineService refinedService;
  @Autowired
  EsMerchandise es;

  @RequestMapping("getByItemId")
  public Result getByItemId(String id) {
    // // Merchandise m = mcdRep.findFirst1ByItemIdAndState(id, "已发布");
    // if (null == m) {
    // return ResultGenerator.genFailResult("未找到已发布的商品编号为：" + id + "的精编信息");
    // }
    RefinedData data = new RefinedData();
    data.setItemId(id);
    // data.setPublishedAt(scRep.find(m.getId()));

    return ResultGenerator.genSuccessResult(data);

  }

  @RequestMapping("merchandise")
  public Result getMerchandise(String start_at, String end_at) {
    System.out.println(start_at + end_at);
    List<Map<String, Object>> list = apiService.merchandise(start_at, end_at);
    if (null == list || list.size() == 0) {
      return ResultGenerator.genSuccessResult();
    }
    return ResultGenerator.genSuccessResult(list);

  }

  // 跳转精编详情
  @RequestMapping("merchandise/{id}")
  @ResponseBody
  public Result EsMerchandise(@PathVariable Long id) {
    Merchandise merchandise = refinedService.toModify(id);
    return ResultGenerator.genSuccessResult(es.getEsMerchandise(merchandise));
  }

}
