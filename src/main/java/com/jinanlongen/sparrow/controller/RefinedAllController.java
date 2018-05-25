package com.jinanlongen.sparrow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.jinanlongen.sparrow.domain.Merchandise;
import com.jinanlongen.sparrow.repository.UserRep;
import com.jinanlongen.sparrow.roc.domain.CacheKey;
import com.jinanlongen.sparrow.service.CacheService;
import com.jinanlongen.sparrow.service.RefinedAllService;
import com.jinanlongen.sparrow.service.RefinedMineService;

/**
 * 
 * @description
 * @author shangyao
 * @date 2018年3月6日
 */
@Controller
@RequestMapping("merchandise/all/")
public class RefinedAllController extends BaseController {
  private static String refinede_html_path = "merchandise/all/";
  @Autowired
  private RefinedAllService refinedAllService;
  @Autowired
  private UserRep userRep;

  @Autowired
  private RefinedMineService refinedService;
  @Autowired
  private CacheService initService;

  @RequestMapping("all")
  public String all(Model model) {
    // model.addAttribute("merchandise", new Merchandise());
    // model.addAttribute("shopList", storeRep.findAll());
    // model.addAttribute("userList", userRep.findByHasRefinedTrue());
    return "redirect:queryAll";
  }

  @RequestMapping("queryAll")
  public String queryAll(Model model, Merchandise merchandise,
      @RequestParam(value = "pageNum", defaultValue = "0") Integer pageNum) {
    merchandise.setPage(pageNum);


    Merchandise mcd = refinedAllService.queryAll(merchandise);
    model.addAttribute("userList", userRep.findByHasRefinedTrue());
    model.addAttribute("merchandise", mcd);
    return refinede_html_path + "all";
  }



  @RequestMapping("{id}")
  public String show(Model model, @PathVariable Long id) {
    Merchandise merchandise = refinedService.toModify(id);

    model.addAttribute("topTaxons", initService.getRocDataList(CacheKey.TOPTAXONS));
    model.addAttribute("merchandise", merchandise);
    return refinede_html_path + "detail";
  }



}
