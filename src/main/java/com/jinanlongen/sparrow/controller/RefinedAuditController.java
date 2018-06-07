package com.jinanlongen.sparrow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.jinanlongen.sparrow.domain.Merchandise;
import com.jinanlongen.sparrow.domain.StateChange;
import com.jinanlongen.sparrow.repository.MerchandiseRep;
import com.jinanlongen.sparrow.repository.StateChangeRep;
import com.jinanlongen.sparrow.roc.domain.CacheKey;
import com.jinanlongen.sparrow.service.CacheService;
import com.jinanlongen.sparrow.service.EsOperationService;
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
@EnableAsync
public class RefinedAuditController extends BaseController {
  private static String refinede_html_path = "merchandise/audit/";
  @Autowired
  private MerchandiseRep mcdRep;
  @Autowired
  private RefinedAuditService refinedAuditService;
  @Autowired
  private StateChangeRep scRep;
  @Autowired
  private StateChangeService scService;
  @Autowired
  private RefinedMineService refinedService;
  @Autowired
  private CacheService initService;
  @Autowired
  private EsOperationService esOperation;

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
    merchandise.setReviewerId(getUserId());

    Merchandise mcd = refinedAuditService.auditQuery(merchandise);
    model.addAttribute("userList",
        refinedAuditService.findUserBySameGroup(merchandise.getReviewerId()));
    model.addAttribute("merchandise", mcd);
    return refinede_html_path + "auditList";
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
    esOperation.postToEs(id, merchandise.getState());
    saveChange(merchandise.getId(), oldeState, newState, mer.getDeclinedReason());

    return "redirect:../mine/" + id;
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
