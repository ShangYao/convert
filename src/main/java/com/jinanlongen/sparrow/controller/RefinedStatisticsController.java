package com.jinanlongen.sparrow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.jinanlongen.sparrow.domain.Merchandise;
import com.jinanlongen.sparrow.repository.UserRep;
import com.jinanlongen.sparrow.service.RefinedStatisticService;

/**
 * 
 * @description
 * @author shangyao
 * @date 2018年3月6日
 */
@Controller
@RequestMapping("merchandise/statistics/")
public class RefinedStatisticsController {
  private static final String BASE_PATH = "merchandise/statistics/";
  @Autowired
  private RefinedStatisticService refinedStatisticService;
  @Autowired
  private UserRep userRep;

  @RequestMapping("statistics")
  public String statistics(Model model) {
    model.addAttribute("userList", userRep.findByHasRefinedTrue());
    model.addAttribute("merchandise", new Merchandise());
    return BASE_PATH + "statistics";
  }

  // 精编统计
  @RequestMapping("staQuery")
  public String staQuery(Model model, Merchandise merchandise) {
    Merchandise mcd = refinedStatisticService.staQuery(merchandise);
    model.addAttribute("userList", userRep.findByHasRefinedTrue());
    model.addAttribute("merchandise", mcd);
    return BASE_PATH + "statistics";
  }
}
