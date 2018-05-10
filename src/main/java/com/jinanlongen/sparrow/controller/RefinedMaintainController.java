package com.jinanlongen.sparrow.controller;

import java.util.List;
import java.util.Set;
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
import com.jinanlongen.sparrow.domain.LineItem;
import com.jinanlongen.sparrow.domain.Maintain;
import com.jinanlongen.sparrow.domain.Merchandise;
import com.jinanlongen.sparrow.domain.SourceUrl;
import com.jinanlongen.sparrow.domain.StateChange;
import com.jinanlongen.sparrow.repository.LineItemRep;
import com.jinanlongen.sparrow.repository.MaintainRep;
import com.jinanlongen.sparrow.repository.MerchandiseRep;
import com.jinanlongen.sparrow.repository.SourceUrlRep;
import com.jinanlongen.sparrow.repository.StateChangeRep;
import com.jinanlongen.sparrow.repository.UserRep;
import com.jinanlongen.sparrow.service.RefinedMaintainService;

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
  private LineItemRep itemRep;
  @Autowired
  private MaintainRep mRep;
  @Autowired
  private UserRep userRep;
  @Autowired
  private SourceUrlRep urlRep;

  @RequestMapping("list")
  public String list(Model model) {
    return "redirect:queryAll";

  }

  @RequestMapping("queryAll")
  public String queryAll(Model model, @ModelAttribute("merchandise") Merchandise merchandise,
      @RequestParam(value = "pageNum", defaultValue = "0") Integer pageNum) {
    merchandise.setPage(pageNum);
    merchandise.setOwnerId(getUserId());
    merchandise.setState("已发布");
    Merchandise mcd = refinedMaintainService.maintainQuery(merchandise);
    mcd.getQueryList().forEach(i -> {
      ((Merchandise) i)
          .setSourceUrl(urlRep.findByMerchandiseIdAndState(((Merchandise) i).getId(), 1));;
    });
    model.addAttribute("merchandise", mcd);
    return basePath + "list";
  }

  @RequestMapping("show")
  public String show(Model model, Long id) {
    Merchandise merchandise = mcdRep.findOne(id);
    Set<LineItem> ids = itemRep.findByMId(merchandise.getId());
    merchandise.setLineItems(ids);
    List<SourceUrl> urls = urlRep.findByMerchandiseIdAndState(merchandise.getId(), 1);
    merchandise.setSourceUrl(urls);
    model.addAttribute("merchandise", merchandise);
    model.addAttribute("changeList", scRep.findLast10ByMerchandiseIdOrderByCreatedAtDesc(id));
    model.addAttribute("maintainList", mRep.findLast10ByMidOrderByCreatedAtDesc(id));
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
    mcdRep.updateSateBuId("已下架", id);
    itemRep.updateState(id);
    saveChange(id, "已发布", "已下架");
    logger.info("下架id为{}的精编信息", id);
    attr.addFlashAttribute("merchandise", merchandise);
    return "redirect:queryAll?pageNum=" + page;
  }

  @RequestMapping("toModify")
  @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
  public String toModify(Long id, Model model) {
    Merchandise merchandise = mcdRep.findOne(id);
    List<SourceUrl> urls = urlRep.findByMerchandiseIdAndState(merchandise.getId(), 1);
    merchandise.setSourceUrl(urls);
    model.addAttribute("merchandise", merchandise);

    return basePath + "modify";
  }

  // 保存修改的精编
  @RequestMapping("modify")
  @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
  public String modify(Merchandise merchandise, String newState, Model model) {

    Merchandise olderMerchandise = mcdRep.findOne(merchandise.getId());

    String oldState = merchandise.getState();
    olderMerchandise.setTitle(merchandise.getTitle());
    // olderMerchandise.setItemId(merchandise.getItemId());
    // olderMerchandise.setUrl(merchandise.getUrl());
    // olderMerchandise.setStore(storeRep.findOne(merchandise.getStoreId()));
    // olderMerchandise.setStoreId(merchandise.getStoreId());
    // if (null != merchandise.getTargetUrl() && merchandise.getTargetUrl().length >
    // 0) {
    // List<String> urlList2 = new ArrayList<String>();
    // for (int a = 0; a < merchandise.getTargetUrl().length; a++) {
    // if (!"".equals(merchandise.getTargetUrl()[a])) {
    //
    // urlList2.add(merchandise.getMpn()[a] + "," + merchandise.getTargetUrl()[a]);
    //
    // }
    // }
    //
    // olderMerchandise.setSourceUrls(urlList2);
    // }
    if ("待审核".equals(newState)) {
      if (!olderMerchandise.getReviewNeeded()) {
        newState = "已发布";
      }
    }
    // if (0 != mcdRep.itemCount2(merchandise.getItemId(), merchandise.getId())) {
    // model.addAttribute("shopList", storeRep.findAll());
    // olderMerchandise.setAlertMessage("无法添加，平台商品编号已存在！！");
    // model.addAttribute("merchandise", olderMerchandise);
    // return basePath + "modify";
    // }
    userRep.update(getUserId());
    olderMerchandise.setState(newState);
    mcdRep.save(olderMerchandise);
    saveChange(merchandise.getId(), oldState, newState);
    return "redirect:queryAll";

  }

  // 跳转添加源网连接
  @RequestMapping("sourceUrl/{urlId}")
  // @DeleteMapping("sourceUrl/delete/{urlId}")
  public String deleteUrl(@PathVariable(name = "urlId") Long urlId, Merchandise merchandise) {
    SourceUrl oldUrl = urlRep.findOne(urlId);
    oldUrl.setState(0);
    urlRep.save(oldUrl);

    // urlRep.updateState(urlId);
    return "redirect:../toModify?id=" + merchandise.getId();
  }

  // 跳转添加源网连接
  @RequestMapping("toUrl")
  public String toUrl(Long id, Model model) {
    SourceUrl url = new SourceUrl();
    url.setMerchandiseId(id);
    model.addAttribute("url", url);
    return basePath + "url";
  }

  // 保存添加源网连接
  @RequestMapping("addUrl")
  public String addUrl(SourceUrl url) {
    url.setState(1);
    urlRep.save(url);

    return "redirect:toModify?id=" + url.getMerchandiseId();
  }

  // 跳转修改源网连接
  @RequestMapping("toModifyUrl")
  public String toUpdateUrl(Long id, Model model) {
    model.addAttribute("url", urlRep.findOne(id));
    return basePath + "updateUrl";
  }

  // 保存修改源网连接
  @RequestMapping("updateUrl")
  @Transactional(rollbackFor = {Exception.class})
  public String updateUrl(SourceUrl url, Model model) {
    SourceUrl oldUrl = urlRep.findOne(url.getId());
    oldUrl.setState(0);
    urlRep.save(oldUrl);

    // urlRep.updateState(url.getId());
    SourceUrl newUrl = new SourceUrl();
    newUrl.setMpn(url.getMpn());
    newUrl.setUrl(url.getUrl());
    newUrl.setMerchandiseId(url.getMerchandiseId());
    newUrl.setState(1);
    urlRep.save(newUrl);
    model.addAttribute("url", newUrl);
    return "redirect:toModify?id=" + url.getMerchandiseId();
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
