package com.jinanlongen.sparrow.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.jinanlongen.sparrow.domain.Album;
import com.jinanlongen.sparrow.domain.Color;
import com.jinanlongen.sparrow.domain.Desc;
import com.jinanlongen.sparrow.domain.Image;
import com.jinanlongen.sparrow.domain.LineItem;
import com.jinanlongen.sparrow.domain.Merchandise;
import com.jinanlongen.sparrow.domain.Size;
import com.jinanlongen.sparrow.domain.SourceUrl;
import com.jinanlongen.sparrow.domain.Spec;
import com.jinanlongen.sparrow.domain.StateChange;
import com.jinanlongen.sparrow.domain.UploadImage;
import com.jinanlongen.sparrow.repository.AlbumRep;
import com.jinanlongen.sparrow.repository.ColorRep;
import com.jinanlongen.sparrow.repository.DescRep;
import com.jinanlongen.sparrow.repository.ImageRep;
import com.jinanlongen.sparrow.repository.LineItemRep;
import com.jinanlongen.sparrow.repository.MerchandiseRep;
import com.jinanlongen.sparrow.repository.SizeRep;
import com.jinanlongen.sparrow.repository.SourceUrlRep;
import com.jinanlongen.sparrow.repository.SpecRep;
import com.jinanlongen.sparrow.repository.StateChangeRep;
import com.jinanlongen.sparrow.repository.UploadImageRep;
import com.jinanlongen.sparrow.repository.UserRep;
import com.jinanlongen.sparrow.roc.domain.CacheKey;
import com.jinanlongen.sparrow.roc.domain.Taxon;
import com.jinanlongen.sparrow.roc.repository.TaxonRep;
import com.jinanlongen.sparrow.service.CacheService;
import com.jinanlongen.sparrow.service.EsOperationService;
import com.jinanlongen.sparrow.service.RefinedMineService;
import com.jinanlongen.sparrow.util.DateUtils;
import com.jinanlongen.sparrow.util.Httptest;
import com.jinanlongen.sparrow.util.MD5Utils;

/**
 * 
 * @description
 * @author shangyao
 * @date 2018年2月12日
 */
@Controller
@RequestMapping("merchandise/mine/")
public class MerchandiseMineController extends BaseController {
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
  @Autowired
  private TaxonRep TaxonRep;
  @Autowired
  private CacheService initService;
  @Autowired
  private AlbumRep albumRep;
  @Autowired
  private ImageRep imageRep;
  @Autowired
  private ColorRep colorRep;
  @Autowired
  private SizeRep sizeRep;
  @Autowired
  private SpecRep specRep;
  @Autowired
  private DescRep descRep;
  @Autowired
  private UploadImageRep uploadrep;
  @Autowired
  private EsOperationService esOperation;

  // 首页
  @RequestMapping("index")
  public String index(Model model) {

    model.addAttribute("user", userRep.findOne(getUserId()));
    Merchandise merchandise = new Merchandise();
    merchandise.setOwnerId(getUserId());
    model.addAttribute("merchandise", refinedService.queryList(merchandise));
    return "index";
  }

  // ------------------------desc------------------
  @RequestMapping("{id}/desc")
  public String desc(@PathVariable Long id, Model model) {

    Desc desc = new Desc();
    desc.setMerchandiseId(id);
    model.addAttribute("desc", desc);
    return BASE_PATH + "desc";
  }

  @RequestMapping("{mid}/desc/{id}")
  public String modifyDesc(@PathVariable Long id, Model model) {
    Desc desc = descRep.findOne(id);
    model.addAttribute("desc", desc);
    return BASE_PATH + "desc";
  }

  @RequestMapping("desc/{id}")
  public String showDesc(@PathVariable Long id, Model model) {
    Desc desc = descRep.findOne(id);
    model.addAttribute("desc", desc);
    return BASE_PATH + "descShow";
  }

  @RequestMapping("desc/show/{id}")
  public String showd(@PathVariable Long id, Model model) {
    Desc desc = descRep.findOne(id);
    model.addAttribute("desc", desc);
    return BASE_PATH + "descShow";
  }

  @RequestMapping("{id}/addDesc")
  public String addDesc(Desc desc) {
    descRep.save(desc);
    return "redirect:modify";
  }

  // ?
  @RequestMapping("{mid}/deleteDesc/{id}")
  public String deleteDesc(@PathVariable Long id) {
    descRep.delete(id);
    return "redirect:../modify";
  }

  @RequestMapping("{id}/modifyDesc")
  public String saveModifyDesc(Desc desc) {
    descRep.save(desc);
    return "redirect:modify";
  }

  // --------------------------spec--------------------
  @RequestMapping("{id}/spec")
  public String spec(@PathVariable Long id, Model model) {
    Spec spec = new Spec();
    spec.setMerchandiseId(id);
    model.addAttribute("spec", spec);
    return BASE_PATH + "spec";
  }

  @RequestMapping("{mid}/spec/{id}")
  public String modifySpec(@PathVariable Long id, Model model) {
    Spec spec = specRep.findOne(id);
    model.addAttribute("spec", spec);
    return BASE_PATH + "spec";
  }

  @RequestMapping("{id}/addSpec")
  public String addSpec(Spec spec) {
    specRep.save(spec);

    return "redirect:modify";
  }

  // ?
  @RequestMapping("{mid}/deleteSpec/{id}")
  public String deleteSpec(@PathVariable Long id) {
    specRep.delete(id);
    return "redirect:../modify";
  }

  @RequestMapping("{id}/modifySpec")
  public String saveModifySpec(Spec spec) {
    specRep.save(spec);
    return "redirect:modify";
  }


  // ------------------------color---------------------
  // 跳转添加color
  @RequestMapping("{id}/color")
  public String color(@PathVariable Long id, Model model) {
    Color color = new Color();
    color.setMerchandiseId(id);
    model.addAttribute("color", color);
    model.addAttribute("albums", albumRep.findByMerchandiseId(id));
    return BASE_PATH + "color";
  }

  @RequestMapping("{mid}/color/{id}")
  public String modifyColor(@PathVariable Long id, Model model) {
    Color color = colorRep.findOne(id);
    model.addAttribute("color", color);
    model.addAttribute("albums", albumRep.findByMerchandiseId(color.getMerchandiseId()));
    return BASE_PATH + "color";
  }

  @RequestMapping("{id}/addColor")
  public String addColor(Color color) {
    int count = colorRep.countColor(color.getName(), color.getMerchandiseId());
    if (count == 0) {
      if (color.getAlbumId() != 0) {
        color.setAlbumName(albumRep.findNameById(color.getAlbumId()));
      }
      colorRep.save(color);
    }
    return "redirect:modify";
  }

  // ?
  @RequestMapping("{mid}/deleteColor/{id}")
  public String deleteColor(@PathVariable Long id) {
    colorRep.delete(id);
    return "redirect:../modify";
  }

  @RequestMapping("{id}/modifyColor")
  public String saveModifyColor(Color color) {
    int count = colorRep.countColor2(color.getName(), color.getMerchandiseId(), color.getId());
    if (count == 0) {
      if (color.getAlbumId() != 0) {
        color.setAlbumName(albumRep.findNameById(color.getAlbumId()));
      }
      colorRep.save(color);
    }
    return "redirect:modify";
  }

  // --------------------------------size-----------------------------
  @RequestMapping("{id}/size")
  public String size(@PathVariable Long id, Model model) {
    Size size = new Size();
    size.setMerchandiseId(id);
    model.addAttribute("size", size);
    model.addAttribute("albums", albumRep.findByMerchandiseId(id));
    return BASE_PATH + "size";
  }

  @RequestMapping("{mid}/size/{id}")
  public String modifysize(@PathVariable Long id, Model model) {
    Size size = sizeRep.findOne(id);
    model.addAttribute("size", size);
    model.addAttribute("albums", albumRep.findByMerchandiseId(size.getMerchandiseId()));
    return BASE_PATH + "size";
  }

  @RequestMapping("{id}/addSize")
  public String addSize(Size size) {
    int count = sizeRep.countSize(size.getName(), size.getMerchandiseId());
    if (count == 0) {
      if (size.getAlbumId() != 0) {
        size.setAlbumName(albumRep.findNameById(size.getAlbumId()));
      }
      sizeRep.save(size);
    }
    return "redirect:modify";
  }

  // ?
  @RequestMapping("{mid}/deleteSize/{id}")
  public String deleteSize(@PathVariable Long id) {
    sizeRep.delete(id);
    return "redirect:../modify";
  }

  @RequestMapping("{id}/modifySize")
  public String saveModifySize(Size size) {
    int count = sizeRep.countSize2(size.getName(), size.getMerchandiseId(), size.getId());
    if (count == 0) {
      if (size.getAlbumId() != 0) {
        size.setAlbumName(albumRep.findNameById(size.getAlbumId()));
      }
      sizeRep.save(size);
    }
    return "redirect:modify";
  }

  // ------------------------------lineItem--------------
  // 跳转添加单品页面
  @RequestMapping("{id}/lineItem")
  public String toAddItem(@PathVariable Long id, Model model) {
    LineItem item = new LineItem();
    item.setmId(id);
    model.addAttribute("item", item);
    model.addAttribute("albums", albumRep.findByMerchandiseId(id));
    model.addAttribute("colors", colorRep.findByMerchandiseId(id));
    model.addAttribute("sizes", sizeRep.findByMerchandiseId(id));
    return BASE_PATH + "itemAdd";
  }

  @RequestMapping("{mid}/lineItem/{id}")
  public String toItem(@PathVariable Long id, Model model) {
    LineItem item = itemRep.findOne(id);
    model.addAttribute("item", item);
    model.addAttribute("albums", albumRep.findByMerchandiseId(item.getmId()));
    model.addAttribute("colors", colorRep.findByMerchandiseId(item.getmId()));
    model.addAttribute("sizes", sizeRep.findByMerchandiseId(item.getmId()));
    return BASE_PATH + "item";
  }

  @RequestMapping("{id}/modifyItem")
  @Transactional(rollbackFor = {Exception.class})
  public String modifyItem(LineItem item) {
    LineItem newItem = itemRep.findOne(item.getId());
    newItem.setAlbumId(item.getAlbumId());
    newItem.setAlbumName(albumRep.findNameById(item.getAlbumId()));
    newItem.setPrice(item.getPrice());
    newItem.setStock(item.getStock());

    itemRep.save(newItem);
    return "redirect:modify";
  }

  @RequestMapping("{mid}/deleteItem/{id}")
  @Transactional(rollbackFor = {Exception.class})
  public String deleteItem(@PathVariable Long id) {
    itemRep.delete(id);
    return "redirect:../modify";
  }

  // 保存添加的单品
  @RequestMapping("{id}/addItem")
  @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
  public String addItem(LineItem item) {
    long[] colorIds = item.getColors();
    long[] sizeIds = item.getSizes();
    LineItem newItem;
    int count;
    for (long cid : colorIds) {
      for (long sid : sizeIds) {
        count = itemRep.countColorAndSize(cid, sid);
        if (count == 0) {

          newItem = new LineItem();
          newItem.setAlbumId(item.getAlbumId());
          newItem.setAlbumName(albumRep.findNameById(item.getAlbumId()));
          newItem.setColor(colorRep.findNameById(cid));
          newItem.setSize(sizeRep.findNameById(sid));
          newItem.setColorId(cid);
          newItem.setSizeId(sid);
          newItem.setPrice(item.getPrice());
          newItem.setStock(item.getStock());
          newItem.setmId(item.getmId());
          itemRep.save(newItem);
        }
      }
    }
    // itemRep.save(item);
    // mcdRep.updateTime(item.getUpdatedAt(), item.getmId());
    return "redirect:modify";
  }

  // -------------------------taxon ----
  @RequestMapping("taxon2")
  @ResponseBody
  public List<Taxon> getTaxon2(String id) {
    Taxon taxon = TaxonRep.findByCode(id).get(0);
    return TaxonRep.findByAncestry(taxon.getId() + "");
  }

  @RequestMapping("taxon3")
  @ResponseBody
  public List<Taxon> getTaxon3(String id) {
    Taxon taxon = TaxonRep.findByCode(id).get(0);
    return TaxonRep.getTaxon3("%" + taxon.getId());
  }

  // ----------------------------------sourceUrl-----------------------------------
  // 跳转添加源网连接
  @RequestMapping("{id}/deleteUrl/{urlId}")
  // @DeleteMapping("sourceUrl/delete/{urlId}")
  public String deleteUrl(@PathVariable(name = "urlId") Long urlId, Merchandise merchandise) {
    SourceUrl oldUrl = urlRep.findOne(urlId);
    oldUrl.setState(0);
    urlRep.save(oldUrl);

    // urlRep.updateState(urlId);
    return "redirect:../modify";
  }

  // 跳转添加源网连接
  @RequestMapping("{id}/url")
  public String toUrl(@PathVariable Long id, Model model) {
    SourceUrl url = new SourceUrl();
    url.setMerchandiseId(id);
    model.addAttribute("url", url);
    return BASE_PATH + "url";
  }

  // 保存添加源网连接
  @RequestMapping("{id}/addUrl")
  public String addUrl(SourceUrl url) {
    url.setState(1);
    urlRep.save(url);
    return "redirect:./modify";
  }

  // 跳转修改源网连接
  @RequestMapping("{mid}/url/{id}")
  public String toUpdateUrl(@PathVariable Long id, Model model) {
    model.addAttribute("url", urlRep.findOne(id));
    return BASE_PATH + "updateUrl";
  }

  // 保存修改源网连接
  @RequestMapping("{mid}/url/update")
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
    return "redirect:../modify";
  }

  // --------------------------- tinymce上传图片
  @RequestMapping("upload")
  @ResponseBody
  public String uploa(MultipartFile thumbnail, long mid) {
    String fileName = generateFileName(mid);
    boolean result = false;;
    try {
      result = Httptest.post(thumbnail.getBytes(), fileName);
      if (result) {
        UploadImage upload = new UploadImage();
        upload.setHref(fileName);
        upload.setMerchandiseId(mid);
        upload.setFingerprint(MD5Utils.MD5(thumbnail.getBytes()));
        uploadrep.save(upload);
        return fileName;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return "http://192.168.200.71:8888/areatrend_b/e0ca50c25f5c3b4957da6145ee3c9878.jpg?mode=2f&width=150&height=150";
  }

  private String generateFileName(long mid) {
    return "http://192.168.200.71:8888/sparrow/" + mid + "/desc/" + DateUtils.dateString() + ".jpg";

  }

  // ---------------------------------------------------album---------------------------------------

  // 跳转相册管理列表
  @RequestMapping("{id}/albums")
  public String albumList(@PathVariable long id, Model model) {
    Merchandise merchandise = new Merchandise();
    merchandise.setId(id);
    merchandise.setAlbums(albumRep.findByMerchandiseId(id));
    model.addAttribute("merchandise", merchandise);
    return BASE_PATH + "albums";
  }

  // 修改相册
  @RequestMapping("toModifyAlbum")
  public String toModifyAlbum(long id, Model model) {
    Album album = albumRep.findOne(id);
    model.addAttribute("album", album);
    return BASE_PATH + "modifyAlbum";
  }

  // 保存修改相册
  @RequestMapping("modifyAlbum")
  public String modifyAlbum(Album album) {
    albumRep.updateName(album.getName(), album.getId());
    return "redirect:" + album.getMerchandiseId() + "/albums";
  }

  // 删除相册
  @RequestMapping("deleteAlbum")
  public String deleteAlbum(long id) {
    Album album = albumRep.findOne(id);
    albumRep.delete(album);
    return "redirect:" + album.getMerchandiseId() + "/albums";
  }

  // 跳转单个相册管理
  @RequestMapping("album/{id}")
  public String album(@PathVariable long id, Model model) {
    Album album = albumRep.findOne(id);
    album.setImages(imageRep.findByAlbumId(id));
    model.addAttribute("album", album);
    model.addAttribute("merchandise", mcdRep.findOne(id));
    return BASE_PATH + "album";
  }

  // 跳转单个相册管理
  @RequestMapping("album/show/{id}")
  public String showAlbum(@PathVariable long id, Model model) {
    Album album = albumRep.findOne(id);
    album.setImages(imageRep.findByAlbumId(id));
    model.addAttribute("album", album);
    model.addAttribute("merchandise", mcdRep.findOne(id));
    return BASE_PATH + "albumShow";
  }

  // 添加相册
  @RequestMapping("addAlbum")
  public String addAlbum(long id, Model model) {
    Album album = new Album();
    album.setMerchandiseId(id);
    model.addAttribute("album", album);
    return BASE_PATH + "addAlbum";
  }

  // 保存添加相册
  @RequestMapping("saveAlbum")
  public String saveAlbum(Album album) {
    albumRep.save(album);
    return "redirect:album/" + album.getId();
  }

  // -------------------------------------image---------------------------------
  // 添加照片
  @RequestMapping("album/addImage")
  public String addImage(long id, Model model) {
    Image image = new Image();
    image.setAlbumId(id);
    model.addAttribute("image", image);
    return BASE_PATH + "addImage";
  }



  // 保存添加图片
  @RequestMapping("album/saveImage")
  @Transactional(rollbackFor = {Exception.class})
  public String saveImage(Image image) {
    System.out.println(image);
    String url = "http://192.168.200.71:8888/sparrow/" + image.getAlbumId() + "/"
        + DateUtils.dateString() + ".jpg";
    boolean result = false;;
    try {
      result = Httptest.post(image.getImageFile().getBytes(), url);
      if (result) {
        image.setFingerprint(MD5Utils.MD5(image.getImageFile().getBytes()));
        image.setUrl(url);
        imageRep.save(image);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return "redirect:./" + image.getAlbumId();
  }

  // 删除照片
  @RequestMapping("album/deleteImage")
  @Transactional(rollbackFor = {Exception.class})
  public String deleteImage(long imageId) {
    Image image = imageRep.findOne(imageId);
    Httptest.delete(image.getUrl());
    imageRep.delete(image);
    return "redirect:./" + image.getAlbumId();
  }

  //
  @RequestMapping("album/image/{id}")
  @Transactional(rollbackFor = {Exception.class})
  public String toModifyImage(@PathVariable long id, Model model) {
    Image image = imageRep.findOne(id);
    model.addAttribute("image", image);
    return BASE_PATH + "image";
  }

  // 修改照片
  @RequestMapping("album/modifyImage")
  @Transactional(rollbackFor = {Exception.class})
  public String modifyImage(Image image) {
    Image oldImage = imageRep.findOne(image.getId());
    oldImage.setCode(image.getCode());
    imageRep.save(oldImage);

    return "redirect:./" + image.getAlbumId();
  }

  // 跳转相册管理
  @RequestMapping("albums")
  @Transactional(rollbackFor = {Exception.class})
  public String album(Merchandise merchandise) {
    merchandise.setOwnerId(getUserId());
    merchandise.setHolderId(getUserId());
    merchandise.setHolderName(getUserName());
    merchandise.setOwnerName(getUserName());
    merchandise.setState("草稿");

    if (null != merchandise.getTaxonId3() && !merchandise.getTaxonId3().equals("")) {
      merchandise.setTaxonId(merchandise.getTaxonId3());
    } else if (null != merchandise.getTaxonId2() && !merchandise.getTaxonId2().equals("")) {
      merchandise.setTaxonId(merchandise.getTaxonId2());
    }
    mcdRep.save(merchandise);
    userRep.update(getUserId());
    StateChange sc = new StateChange(merchandise.getId(), "", "草稿", "新建精编");
    saveChange(sc);
    return "redirect:" + merchandise.getId() + "/albums";
  }

  // 跳转新创建精编页
  @RequestMapping("toAdd")
  public String toAdd(Model model) {

    model.addAttribute("merchandise", new Merchandise());
    model.addAttribute("brands", initService.getRocDataList(CacheKey.BRANDS));
    model.addAttribute("genders", initService.getRocDataList(CacheKey.GENDERS));
    model.addAttribute("topTaxons", initService.getRocDataList(CacheKey.TOPTAXONS));
    return BASE_PATH + "add";
  }

  // 修改精编基础信息
  @RequestMapping("{id}/basic/toModify")
  public String toModifyBasic(Model model, @PathVariable Long id) {

    return "redirect:../basic";
  }

  // 跳转修改精编页
  @RequestMapping("{id}/basic")
  public String toModify(Model model, @PathVariable Long id) {
    Merchandise merchandise = refinedService.toModify(id);
    merchandise.setAlbums(albumRep.findByMerchandiseId(id));
    model.addAttribute("brands", initService.getRocDataList(CacheKey.BRANDS));
    model.addAttribute("genders", initService.getRocDataList(CacheKey.GENDERS));
    model.addAttribute("topTaxons", initService.getRocDataList(CacheKey.TOPTAXONS));
    model.addAttribute("merchandise", merchandise);
    return BASE_PATH + "basic";
  }

  @InitBinder
  public void initBinder(WebDataBinder binder, WebRequest request) {
    // 转换日期
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// CustomDateEditor为自定义日期编辑器
  }

  // 保存基本信息
  @RequestMapping("{id}/basic/save")
  @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
  public String saveBasic(Merchandise merchandise) {
    // saveMerchandise(merchandise, merchandise.getState());
    Merchandise newM = mcdRep.findOne(merchandise.getId());
    newM.setTitle(merchandise.getTitle());
    newM.setAlbumId(merchandise.getAlbumId());
    newM.setBrandId(merchandise.getBrandId());
    newM.setGenderId(merchandise.getGenderId());

    if (null != merchandise.getTaxonId3() && !merchandise.getTaxonId3().equals("")) {
      newM.setTaxonId(merchandise.getTaxonId3());
    } else if (null != merchandise.getTaxonId2() && !merchandise.getTaxonId2().equals("")) {
      newM.setTaxonId(merchandise.getTaxonId2());
    }
    mcdRep.save(newM);
    return "redirect:../modify";

  }



  // --------------------------------------修改精编----------------------------
  // 保存修改精编-draft
  @RequestMapping("{id}/draft")
  @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
  public String draft(Merchandise merchandise) {
    merchandise = mcdRep.findOne(merchandise.getId());
    StateChange sc = new StateChange(merchandise.getId(), merchandise.getState(), "草稿", "修改精编");
    saveChange(sc);
    merchandise.setState("草稿");
    mcdRep.save(merchandise);
    if (sc.getOldState().equals("已发布") && sc.getNewState().equals("草稿")) {
      esOperation.postToEs(merchandise.getId(), "草稿");
    }
    return "redirect:../queryList";

  }

  // 保存修改精编-publish
  @RequestMapping("{id}/publish")
  @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
  public String publish(Merchandise merchandise) {
    StateChange sc;
    merchandise = mcdRep.findOne(merchandise.getId());
    if (merchandise.getReviewNeeded()) {
      sc = new StateChange(merchandise.getId(), merchandise.getState(), "待审核", "修改精编");

      merchandise.setState("待审核");
    } else {
      sc = new StateChange(merchandise.getId(), merchandise.getState(), "已发布", "修改精编");

      merchandise.setState("已发布");
    }
    mcdRep.save(merchandise);
    if (sc.getOldState().equals("已发布") && sc.getNewState().equals("待审核")) {
      esOperation.postToEs(merchandise.getId(), "待审核");
    } else if (sc.getOldState().equals("已发布") && sc.getNewState().equals("已发布")) {
      esOperation.postToEs(merchandise.getId(), "已发布");
    }
    saveChange(sc);
    return "redirect:../queryList";

  }

  // 跳转修改精编页
  @RequestMapping("{id}/modify")
  public String toModify2(Model model, @PathVariable Long id) {
    Merchandise merchandise = refinedService.toModify(id);

    model.addAttribute("topTaxons", initService.getRocDataList(CacheKey.TOPTAXONS));
    model.addAttribute("merchandise", merchandise);
    return BASE_PATH + "modify";
  }



  // 跳转精编详情
  @RequestMapping("{id}")
  public String show(Model model, @PathVariable Long id) {
    Merchandise merchandise = refinedService.toModify(id);
    model.addAttribute("topTaxons", initService.getRocDataList(CacheKey.TOPTAXONS));
    model.addAttribute("merchandise", merchandise);
    return BASE_PATH + "detail";
  }

  // 删除精编
  @RequestMapping("{id}/delete")
  public String delete(@PathVariable Long id, Merchandise m, RedirectAttributes model) {
    Merchandise merchandise = mcdRep.findOne(id);
    StateChange sc = new StateChange(id, merchandise.getState(), "回收站", "删除精编");
    saveChange(sc);
    if (merchandise.getState().equals("已发布")) {
      esOperation.deleteOne(id);
    }
    merchandise.setState("回收站");
    merchandise.setReviewNeeded(true);
    mcdRep.save(merchandise);
    model.addFlashAttribute("redirectAttr", m);
    return "redirect:../queryList";
  }


  @RequestMapping("{id}/enable")
  public String enable(@PathVariable Long id, Merchandise m, RedirectAttributes model) {
    Merchandise merchandise = mcdRep.findOne(id);
    merchandise.setState("已发布");
    mcdRep.save(merchandise);
    StateChange sc = new StateChange(id, "已禁用", "已发布", "");
    saveChange(sc);
    esOperation.updateDocumentState(id + "", "已发布");
    model.addFlashAttribute("redirectAttr", m);
    return "redirect:../queryList";
  }

  @RequestMapping("{id}/disable")
  public String disable(@PathVariable Long id, Merchandise m, RedirectAttributes model) {
    Merchandise merchandise = mcdRep.findOne(id);
    merchandise.setState("已禁用");
    mcdRep.save(merchandise);
    StateChange sc = new StateChange(id, "已发布", "已禁用", "");
    saveChange(sc);
    esOperation.updateDocumentState(id + "", "已禁用");
    model.addFlashAttribute("redirectAttr", m);
    return "redirect:../queryList";
  }

  // 跳转我的精编列表页
  @RequestMapping("list")
  public String list(Model model) {
    return "redirect:queryList";
  }

  // 我的精编-查询
  @RequestMapping("queryList")
  public String queryList(Model model, Merchandise merchandise,
      @RequestParam(value = "show", defaultValue = "") String show,
      @RequestParam(value = "pageNum", defaultValue = "0") Integer pageNum) {
    // merchandise.setTabShow(show == null ? "" : show);
    if (model.containsAttribute("redirectAttr")) {
      merchandise = (Merchandise) model.asMap().get("redirectAttr");
      pageNum = merchandise.getRedirectPage();
    }
    if (null == show || "".equals(show)) {
      show = merchandise.getTabShow();
    }
    switch (show) {
      case "all":
        merchandise.setPage(pageNum);
        break;
      case "draft":
        merchandise.setDraftPageNum(pageNum);
        break;
      case "committed":
        merchandise.setCommittedPageNum(pageNum);
        break;
      case "published":
        merchandise.setPublishedPageNum(pageNum);
        break;
      case "declined":
        merchandise.setDeclinedPageNum(pageNum);
        break;
      case "retired":
        merchandise.setRetiredPageNum(pageNum);
        break;
      case "trash":
        merchandise.setTrashPageNum(pageNum);
        break;
      default:
        merchandise.setPage(pageNum);
        break;
    }
    merchandise.setOwnerId(getUserId());
    Merchandise mcd = refinedService.queryList(merchandise);
    model.addAttribute("merchandise", mcd);
    return BASE_PATH + "list";
  }

  private void saveChange(StateChange sc) {
    sc.setCreatedUserId(getUserId());
    sc.setCreatedBy(getUserName());
    scRep.save(sc);
  }

}
