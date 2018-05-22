package com.jinanlongen.sparrow.controller;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.jinanlongen.sparrow.domain.LineItem;
import com.jinanlongen.sparrow.domain.Merchandise;
import com.jinanlongen.sparrow.domain.SourceUrl;
import com.jinanlongen.sparrow.domain.StateChange;
import com.jinanlongen.sparrow.repository.LineItemRep;
import com.jinanlongen.sparrow.repository.MerchandiseRep;
import com.jinanlongen.sparrow.repository.SourceUrlRep;
import com.jinanlongen.sparrow.repository.StateChangeRep;
import com.jinanlongen.sparrow.repository.UserRep;
import com.jinanlongen.sparrow.service.RefinedMineService;

/**
 * 
 * @description
 * @author shangyao
 * @date 2018年2月12日
 */
@Controller
@RequestMapping("merchandise/")
public class RefinedMineController extends BaseController {
  private static final String BASE_PATH = "merchandise/mine/";

  @Autowired
  private MerchandiseRep mcdRep;
  @Autowired
  private LineItemRep itemRep;
  @Autowired
  private RefinedMineService refinedService;
  @Autowired
  private UserRep userRep;
  @Autowired
  private StateChangeRep scRep;
  @Autowired
  private SourceUrlRep urlRep;



  // 首页
  @RequestMapping("index")
  public String index(Model model) {

    model.addAttribute("user", userRep.findOne(getUserId()));
    Merchandise merchandise = new Merchandise();
    merchandise.setOwnerId(getUserId());
    model.addAttribute("merchandise", refinedService.queryList(merchandise));
    return "index";
  }



  // 我的精编-删除
  @RequestMapping("delete")
  @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
  public String delete(Long id) {
    Merchandise merchandise = mcdRep.findOne(id);
    if (null != merchandise.getState() && "回收站".equals(merchandise.getState())) {
      mcdRep.delete(id);
      saveChange(id, merchandise.getState(), "彻底删除");
      return "redirect:queryList";
    }
    if (null != merchandise.getState() && "已下架".equals(merchandise.getState())) {
      mcdRep.delete(id);
      saveChange(id, merchandise.getState(), "彻底删除");
      return "redirect:queryList";
    }
    if (null != merchandise.getState() && "已发布".equals(merchandise.getState())) {
      mcdRep.updateSateBuId("已下架", id);
      itemRep.updateState(id);
      saveChange(id, merchandise.getState(), "已下架");
      return "redirect:queryList";
    }
    saveChange(id, merchandise.getState(), "回收站");
    mcdRep.updateSateBuId("回收站", id);
    return "redirect:queryList";
  }

  // 跳转详情页
  // @RequestMapping("{id}")
  public String show(Model model, @PathVariable Long id) {
    Merchandise merchandise = mcdRep.findOne(id);
    Set<LineItem> ids = itemRep.findByMId(merchandise.getId());
    merchandise.setLineItems(ids);

    List<SourceUrl> urls = urlRep.findByMerchandiseIdAndState(merchandise.getId(), 1);
    merchandise.setSourceUrl(urls);
    model.addAttribute("merchandise", merchandise);
    model.addAttribute("changeList", scRep.findLast10ByMerchandiseIdOrderByCreatedAtDesc(id));
    return BASE_PATH + "detail";
  }

  // 跳转设置单品页面
  @RequestMapping("set")
  public String set(Long id, Model model) {
    Merchandise merchandise = mcdRep.findOne(id);
    Set<LineItem> ids = itemRep.findByMId(merchandise.getId());
    merchandise.setLineItems(ids);
    List<SourceUrl> urls = urlRep.findByMerchandiseIdAndState(merchandise.getId(), 1);
    merchandise.setSourceUrl(urls);
    model.addAttribute("merchandise", merchandise);
    return BASE_PATH + "set";
  }

  // 保存设置单品
  @RequestMapping("setItem")
  public String setItem(Model model, Merchandise merchandise, String state) {
    Merchandise newMerchandise = mcdRep.findOne(merchandise.getId());
    // if (0 != mcdRep.itemCount(merchandise.getItemId())) {
    // merchandise.setAlertMessage("无法添加，平台商品编号已存在！！");
    // model.addAttribute("merchandise", newMerchandise);
    // return BASE_PATH + "set";
    // }

    List<BigInteger> ids = itemRep.findIdByMId(merchandise.getId());
    Set<LineItem> items = new HashSet<>();

    if (null != merchandise.getItemIds() && "" != merchandise.getItemIds()) {
      String[] itemIds = merchandise.getItemIds().split(",");
      for (String string : itemIds) {
        items.add(itemRep.findOne(Long.parseLong(string)));
      }

    }
    newMerchandise.setLineItems(items);
    if ("待审核".equals(state)) {
      if (!newMerchandise.getReviewNeeded()) {
        state = "已发布";
      }
    }
    saveChange(newMerchandise.getId(), newMerchandise.getState(), state);
    newMerchandise.setState(state);
    mcdRep.save(newMerchandise);
    for (BigInteger in : ids) {
      if (!merchandise.getItemIds().contains(in + ",")) {
        itemRep.delete(Long.parseLong(in + ""));
      }
    }
    return "redirect:queryList";
  }

  // 修改单品信息
  @RequestMapping("toModifyItem")
  public String toModifyItem(Model model, Long id) {
    model.addAttribute("item", itemRep.findOne(id));
    return BASE_PATH + "itemModify";
  }

  // 保存修改的单品
  @RequestMapping("modifyItem")
  @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
  public String modify(LineItem item, Model model) {
    int rocidCount = 0;

    int count = itemRep.count(item.getmId(), item.getColor(), item.getSize(), item.getId());
    if (count > 0) {
      item.setAlertMessage("单品已存在，请重新输入颜色，尺码！");
      model.addAttribute("item", item);
      return BASE_PATH + "itemModify";
    }
    if (rocidCount > 0) {
      item.setAlertMessage("店铺内rocid已存在，重新添加！");
      model.addAttribute("item", item);
      return BASE_PATH + "itemModify";
    }
    itemRep.save(item);
    mcdRep.updateTime(new Date(), item.getmId());

    int count3 = itemRep.count3(item.getmId());
    if (count3 == 0) {
      mcdRep.updateSateBuId("已下架", item.getmId());
    }

    return "redirect:set?id=" + item.getmId();
  }

  @RequestMapping("toCopy")
  public String toCopy(Model model, Long id) {
    Merchandise merchandise = mcdRep.findOne(id);
    merchandise.setSourceUrl(urlRep.findByMerchandiseIdAndState(id, 1));
    model.addAttribute("merchandise", merchandise);
    return BASE_PATH + "copy";
  }

  // 删除单品信息
  @RequestMapping("deleteItem")
  @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
  public String deleteItem(Long itemid, Model model, Merchandise merchandise) {
    itemRep.delete(itemid);
    mcdRep.updateTime(new Date(), merchandise.getId());
    return "redirect:set?id=" + merchandise.getId();
  }

  // 跳转添加单品页面
  @RequestMapping("toItem")
  public String toItem(Long id, Model model) {
    LineItem item = new LineItem();
    item.setmId(id);
    model.addAttribute("item", item);
    return BASE_PATH + "item";
  }

  // 保存添加的单品
  @RequestMapping("addItem")
  @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
  public String addItem(LineItem item, Model model) {
    int rocidCount = 0;

    int count = itemRep.count2(item.getmId(), item.getColor(), item.getSize());
    if (count > 0) {
      item.setAlertMessage("单品已存在，请重新输入颜色，尺码！");
      model.addAttribute("item", item);
      return BASE_PATH + "item";
    }
    if (rocidCount > 0) {
      item.setAlertMessage("店铺内rocid已存在，重新添加！");
      model.addAttribute("item", item);
      return BASE_PATH + "item";
    }
    itemRep.save(item);
    mcdRep.updateTime(item.getUpdatedAt(), item.getmId());
    return "redirect:set?id=" + item.getmId();
  }



  // 保存修改的精编-draft
  @RequestMapping("modify")
  @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
  public String modify(Merchandise merchandise, String newState, Model model) {

    Merchandise olderMerchandise = mcdRep.findOne(merchandise.getId());

    String oldState = merchandise.getState();
    olderMerchandise.setTitle(merchandise.getTitle());

    if ("待审核".equals(newState)) {
      if (!olderMerchandise.getReviewNeeded()) {
        newState = "已发布";
      }
    }

    userRep.update(getUserId());
    olderMerchandise.setState(newState);
    mcdRep.save(olderMerchandise);
    saveChange(merchandise.getId(), oldState, newState);
    return "redirect:queryList";

  }

  // 保存创建的精编
  @RequestMapping("add")
  @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
  public String add(Merchandise merchandise, String newState, Model model) {

    String oldState = merchandise.getState();
    merchandise.setOwnerId(getUserId());
    // merchandise.setOwner(getUser());
    // merchandise.setStore(storeRep.findOne(merchandise.getStoreId()));
    // if (0 != mcdRep.itemCount(merchandise.getItemId())) {
    // model.addAttribute("shopList", storeRep.findAll());
    // merchandise.setAlertMessage("无法添加，平台商品编号已存在！！");
    // model.addAttribute("merchandise", merchandise);
    // return BASE_PATH + "add";
    // }

    userRep.update(getUserId());
    merchandise.setState(newState);
    mcdRep.save(merchandise);

    if (null != merchandise.getTargetUrl() && merchandise.getTargetUrl().length > 0) {
      // List<SourceUrl> urlList = new ArrayList<SourceUrl>();
      // List<String> urlList2 = new ArrayList<String>();
      SourceUrl url;
      for (int a = 0; a < merchandise.getTargetUrl().length; a++) {
        if (!"".equals(merchandise.getTargetUrl()[a])) {
          // SourceUrl u = new SourceUrl();
          // u.setUrl(merchandise.getTargetUrl()[a]);
          // u.setMpn(merchandise.getMpn()[a]);
          // urlRep.save(u);
          // urlList.add(u);
          url = new SourceUrl();
          url.setMpn(merchandise.getMpn()[a]);
          url.setMerchandiseId(merchandise.getId());
          url.setUrl(merchandise.getTargetUrl()[a]);
          url.setState(1);
          urlRep.save(url);
          // urlList2.add(merchandise.getMpn()[a] + "," + merchandise.getTargetUrl()[a]);

        }
      }

      // merchandise.setSources(urlList);
      // merchandise.setSourceUrls(urlList2);
    }

    saveChange(merchandise.getId(), oldState, newState);
    return "redirect:queryList";

  }

  // 精编复制，为复制单品信息
  @RequestMapping("copy")
  @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
  public String copy(Merchandise merchandise, String newState, Model model) {
    // if (0 != mcdRep.itemCount(merchandise.getItemId())) {
    // model.addAttribute("shopList", storeRep.findAll());
    // merchandise = mcdRep.findOne(merchandise.getOldId());
    // merchandise.setSourceUrl(urlRep.findByMerchandiseIdAndState(merchandise.getOldId(), 1));
    // model.addAttribute("merchandise", merchandise);
    // model.addAttribute("message", "平台商品编号已存在！！");
    // return BASE_PATH + "copy";
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

    return "redirect:queryList";

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
