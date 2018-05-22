package com.jinanlongen.sparrow.controller;

import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import com.jinanlongen.sparrow.roc.domain.CacheKey;
import com.jinanlongen.sparrow.service.CacheService;
import com.jinanlongen.sparrow.service.RefinedAuditService;
import com.jinanlongen.sparrow.service.RefinedMineService;
import com.jinanlongen.sparrow.service.StateChangeService;

/**
 * 
 * @description
 * @author shangyao
 * @date 2018年3月6日
 */
@Controller
@RequestMapping("merchandise/audit/")
public class RefinedAuditController extends BaseController {
  private static String refinede_html_path = "merchandise/audit/";
  @Autowired
  private MerchandiseRep mcdRep;
  @Autowired
  private RefinedAuditService refinedAuditService;
  @Autowired
  private UserRep userRep;
  @Autowired
  private StateChangeRep scRep;
  @Autowired
  private StateChangeService scService;
  @Autowired
  private LineItemRep itemRep;
  @Autowired
  private SourceUrlRep urlRep;
  @Autowired
  private RefinedMineService refinedService;
  @Autowired
  private CacheService initService;

  @RequestMapping("audit")
  public String audit() {
    return "redirect:auditQuery";
  }

  // 精编审核-查询
  @RequestMapping("auditQuery")
  public String auditQuery(Model model, Merchandise merchandise,
      @RequestParam(value = "pageNum", defaultValue = "0") Integer pageNum) {
    merchandise.setState("待审核");
    merchandise.setPage(pageNum);


    Merchandise mcd = refinedAuditService.auditQuery(merchandise);
    model.addAttribute("userList", userRep.findByHasRefinedTrue());
    model.addAttribute("merchandise", mcd);
    return refinede_html_path + "auditList";
  }

  // 跳转精编审核页
  @RequestMapping("toAudit")
  public String toAuditold(Model model, Long id) {
    Merchandise merchandise = mcdRep.findOne(id);
    Set<LineItem> ids = itemRep.findByMId(merchandise.getId());
    merchandise.setLineItems(ids);
    List<SourceUrl> urls = urlRep.findByMerchandiseIdAndState(merchandise.getId(), 1);
    merchandise.setSourceUrl(urls);
    model.addAttribute("merchandise", merchandise);
    return refinede_html_path + "audit";
  }

  // 跳转精编详情
  @RequestMapping("{id}")
  public String toAudit(Model model, @PathVariable Long id) {
    Merchandise merchandise = refinedService.toModify(id);

    model.addAttribute("topTaxons", initService.getRocDataList(CacheKey.TOPTAXONS));
    model.addAttribute("merchandise", merchandise);
    return refinede_html_path + "audit";
  }

  // 保存审核结果
  @RequestMapping("audited")
  public String audited(Long id, String state, Merchandise mer) {
    Merchandise merchandise = mcdRep.findOne(id);
    String oldeState = merchandise.getState();
    String newState = state;
    // merchandise.setReviewer(getUser());
    if ("已发布".equals(state)) {
      merchandise.setState(state);
      merchandise.setReviewNeeded(false);
    } else {
      merchandise.setReviewNeeded(true);
      if (merchandise.getDeclinedCount() >= 1) {
        merchandise.setState("回收站");
        newState = "回收站";
      } else {
        merchandise.setState(state);
      }
      merchandise.setDeclinedCount(merchandise.getDeclinedCount() + 1);
    }
    merchandise.setReviewerId(getUserId());
    merchandise.setReviewerName(getUserName());
    mcdRep.save(merchandise);
    saveChange(merchandise.getId(), oldeState, newState, mer.getDeclinedReason());

    return "redirect:../" + id;
  }

  // 跳转详情页
  @RequestMapping("show")
  public String show(Model model, Long id) {
    if (mcdRep.exists(id)) {

      Merchandise merchandise = mcdRep.findOne(id);
      Set<LineItem> ids = itemRep.findByMId(merchandise.getId());
      merchandise.setLineItems(ids);
      List<SourceUrl> urls = urlRep.findByMerchandiseIdAndState(merchandise.getId(), 1);
      merchandise.setSourceUrl(urls);
      model.addAttribute("merchandise", merchandise);
    } else {
      model.addAttribute("merchandise", new Merchandise());
    }
    model.addAttribute("changeList", scRep.findLast10ByMerchandiseIdOrderByCreatedAtDesc(id));

    return refinede_html_path + "detail";
  }

  @RequestMapping("myAudit")
  public String myAudit(Model model) {
    return "redirect:myAudit";
  }

  @RequestMapping("myQuery")
  public String myQuery(Model model, StateChange stateChange,
      @RequestParam(value = "pageNum", defaultValue = "0") int pageNum) {
    stateChange.setPage(pageNum);
    stateChange.setCreatedUserId(getUserId());
    // stateChange.setMerchandiseId(Long.parseLong(stateChange.getMid()));
    StateChange mcd = scService.query(stateChange);
    model.addAttribute("stateChange", mcd);
    return refinede_html_path + "myAudit";
  }

  private void saveChange(Long id, String oldeState, String newState, String note) {
    StateChange sc = new StateChange();
    sc.setMerchandiseId(id);
    sc.setOldState(oldeState);
    sc.setNewState(newState);
    sc.setCreatedUserId(getUserId());
    sc.setCreatedBy(getUserName());
    sc.setNote(note);
    scRep.save(sc);
  }
}
