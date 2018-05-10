package com.jinanlongen.sparrow.controller;

import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.jinanlongen.sparrow.domain.LineItem;
import com.jinanlongen.sparrow.domain.Merchandise;
import com.jinanlongen.sparrow.domain.SourceUrl;
import com.jinanlongen.sparrow.domain.StateChange;
import com.jinanlongen.sparrow.repository.LineItemRep;
import com.jinanlongen.sparrow.repository.MerchandiseRep;
import com.jinanlongen.sparrow.repository.SourceUrlRep;
import com.jinanlongen.sparrow.repository.StateChangeRep;
import com.jinanlongen.sparrow.repository.UserRep;
import com.jinanlongen.sparrow.service.RefinedAllService;

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
  private MerchandiseRep mcdRep;
  @Autowired
  private StateChangeRep scRep;
  @Autowired
  private LineItemRep itemRep;
  @Autowired
  private SourceUrlRep urlRep;

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

  @RequestMapping("toCopy")
  public String toCopy(Model model, Long id) {
    Merchandise merchandise = mcdRep.findOne(id);
    merchandise.setSourceUrl(urlRep.findByMerchandiseIdAndState(id, 1));
    model.addAttribute("merchandise", merchandise);
    return refinede_html_path + "copy";
  }

  // 精编复制，为复制单品信息
  @RequestMapping("add")
  @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
  public String add(Merchandise merchandise, String newState, Model model) {
    // if (0 != mcdRep.itemCount(merchandise.getItemId())) {
    // model.addAttribute("shopList", storeRep.findAll());
    // merchandise = mcdRep.findOne(merchandise.getOldId());
    // merchandise.setSourceUrl(urlRep.findByMerchandiseIdAndState(merchandise.getOldId(), 1));
    // model.addAttribute("merchandise", merchandise);
    // model.addAttribute("message", "平台商品编号已存在！！");
    // return refinede_html_path + "copy";
    // }

    String oldState = merchandise.getState();
    merchandise.setOwnerId(getUserId());
    // merchandise.setOwner(getUser());
    // merchandise.setStore(storeRep.findOne(merchandise.getStoreId()));

    userRep.update(getUserId());
    merchandise.setState(newState);
    mcdRep.save(merchandise);
    saveChange(merchandise.getId(), oldState, newState);

    SourceUrl newSourceUrl;
    List<SourceUrl> oldUrls = urlRep.findByMerchandiseIdAndState(merchandise.getOldId(), 1);
    for (SourceUrl sourceUrl : oldUrls) {
      newSourceUrl = new SourceUrl();
      newSourceUrl.setMpn(sourceUrl.getMpn());
      newSourceUrl.setUrl(sourceUrl.getUrl());
      newSourceUrl.setMerchandiseId(merchandise.getId());
      urlRep.save(newSourceUrl);
    }

    return "redirect:queryAll";

  }

  @RequestMapping("show/{id}")
  public String show(Model model, @PathVariable("id") Long id) {
    Merchandise merchandise = mcdRep.findOne(id);
    Set<LineItem> ids = itemRep.findByMId(merchandise.getId());
    merchandise.setLineItems(ids);
    List<SourceUrl> urls = urlRep.findByMerchandiseIdAndState(merchandise.getId(), 1);
    merchandise.setSourceUrl(urls);
    model.addAttribute("merchandise", merchandise);
    model.addAttribute("changeList", scRep.findLast10ByMerchandiseIdOrderByCreatedAtDesc(id));
    return refinede_html_path + "detail";
  }

  private void saveChange(Long id, String oldeState, String newState) {
    StateChange sc = new StateChange();
    sc.setMerchandiseId(id);
    sc.setOldState(oldeState);
    sc.setNewState(newState);
    sc.setCreatedUserId(getUserId());
    sc.setCreatedBy(getUserName());
    scRep.save(sc);
  }
}
