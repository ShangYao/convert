package com.jinanlongen.sparrow.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.jinanlongen.sparrow.domain.Maintain;
import com.jinanlongen.sparrow.domain.Merchandise;
import com.jinanlongen.sparrow.domain.StateChange;
import com.jinanlongen.sparrow.repository.MaintainRep;
import com.jinanlongen.sparrow.repository.MerchandiseRep;
import com.jinanlongen.sparrow.repository.SourceUrlRep;
import com.jinanlongen.sparrow.repository.StateChangeRep;
import com.jinanlongen.sparrow.roc.domain.CacheKey;
import com.jinanlongen.sparrow.service.CacheService;
import com.jinanlongen.sparrow.service.EsOperationService;
import com.jinanlongen.sparrow.service.RefinedMaintainService;
import com.jinanlongen.sparrow.service.RefinedMineService;

/**
 * 
 * @description
 * @author shangyao
 * @date 2018年3月20日
 */
@Controller
@RequestMapping("merchandise/maintain/")
public class RefinedMaintainController extends BaseController {
  private static String basePath = "merchandise/maintain/";
  private static Logger logger = LoggerFactory.getLogger(RefinedMaintainController.class);
  @Autowired
  private MerchandiseRep mcdRep;
  @Autowired
  private RefinedMaintainService refinedMaintainService;
  @Autowired
  private StateChangeRep scRep;
  @Autowired
  private MaintainRep mRep;
  @Autowired
  private SourceUrlRep urlRep;
  @Autowired
  private RefinedMineService refinedService;
  @Autowired
  private CacheService initService;
  @Autowired
  private EsOperationService esOperation;

  @RequestMapping("list")
  public String list(Model model) {
    return "redirect:queryAll";

  }

  @RequestMapping("queryAll")
  public String queryAll(Model model, @ModelAttribute("merchandise") Merchandise merchandise,
      @RequestParam(value = "pageNum", defaultValue = "0") Integer pageNum) {
    merchandise.setPage(pageNum);
    merchandise.setOwnerId(getUserId());
    Merchandise mcd = refinedMaintainService.maintainQuery(merchandise);
    mcd.getQueryList().forEach(i -> {
      ((Merchandise) i)
          .setSourceUrl(urlRep.findByMerchandiseIdAndState(((Merchandise) i).getId(), 1));;
    });
    model.addAttribute("merchandise", mcd);
    return basePath + "list";
  }

  @RequestMapping("{id}")
  public String show(Model model, @PathVariable Long id) {
    Merchandise merchandise = refinedService.toModify(id);
    model.addAttribute("topTaxons", initService.getRocDataList(CacheKey.TOPTAXONS));
    model.addAttribute("merchandise", merchandise);
    return basePath + "detail";
  }


  @RequestMapping("maintain")
  @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
  public String maintain(Long id, int page, Merchandise merchandise, RedirectAttributes attr) {
    Maintain maintain = new Maintain();
    maintain.setMid(id);
    maintain.setCreatedBy(getUserName());
    Maintain m = mRep.save(maintain);
    mcdRep.updateMaintainTime(m.getCreatedAt(), id);
    logger.info("维护id为{}的精编信息，当前时间为：{}", id, m.getCreatedAt());
    attr.addFlashAttribute("merchandise", merchandise);
    return "redirect:queryAll?pageNum=" + page;
  }

  @RequestMapping("remove")
  @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
  public String remove(Long id, int page, Merchandise merchandise, RedirectAttributes attr) {
    Merchandise newMerchandise = mcdRep.findOne(id);
    newMerchandise.setState("已禁用");
    mcdRep.save(newMerchandise);
    saveChange(id, "已发布", "已禁用");
    esOperation.updateDocumentState(id + "", "已禁用");
    attr.addFlashAttribute("merchandise", merchandise);
    return "redirect:queryAll?pageNum=" + page;
  }

  @RequestMapping("enable")
  @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
  public String enable(Long id, int page, Merchandise merchandise, RedirectAttributes attr) {
    Merchandise newMerchandise = mcdRep.findOne(id);
    newMerchandise.setState("已发布");
    mcdRep.save(newMerchandise);
    esOperation.updateDocumentState(id + "", "已发布");
    saveChange(id, "已禁用", "已发布");
    attr.addFlashAttribute("merchandise", merchandise);
    return "redirect:queryAll?pageNum=" + page;
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
