package com.jinanlongen.sparrow.controller;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.jinanlongen.sparrow.domain.Album;
import com.jinanlongen.sparrow.domain.Color;
import com.jinanlongen.sparrow.domain.Image;
import com.jinanlongen.sparrow.domain.LineItem;
import com.jinanlongen.sparrow.domain.Merchandise;
import com.jinanlongen.sparrow.domain.Size;
import com.jinanlongen.sparrow.domain.SourceUrl;
import com.jinanlongen.sparrow.domain.StateChange;
import com.jinanlongen.sparrow.repository.AlbumRep;
import com.jinanlongen.sparrow.repository.ColorRep;
import com.jinanlongen.sparrow.repository.ImageRep;
import com.jinanlongen.sparrow.repository.LineItemRep;
import com.jinanlongen.sparrow.repository.MerchandiseRep;
import com.jinanlongen.sparrow.repository.SizeRep;
import com.jinanlongen.sparrow.repository.SourceUrlRep;
import com.jinanlongen.sparrow.repository.StateChangeRep;
import com.jinanlongen.sparrow.repository.UserRep;
import com.jinanlongen.sparrow.roc.domain.CacheKey;
import com.jinanlongen.sparrow.roc.domain.Taxon;
import com.jinanlongen.sparrow.roc.repository.BrandRep;
import com.jinanlongen.sparrow.roc.repository.GenderRep;
import com.jinanlongen.sparrow.roc.repository.TaxonRep;
import com.jinanlongen.sparrow.service.CacheService;
import com.jinanlongen.sparrow.service.RefinedMineService;
import com.jinanlongen.sparrow.util.DateUtils;
import com.jinanlongen.sparrow.util.Httptest;

/**
 * 
 * @description
 * @author shangyao
 * @date 2018年2月12日
 */
@Controller
@RequestMapping("merchandise/")
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
  private BrandRep brandRep;
  @Autowired
  private GenderRep genderRep;
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
    item.setAlbumName(albumRep.findNameById(item.getAlbumId()));
    item.setColor(colorRep.findNameById(item.getColorId()));
    item.setSize(sizeRep.findNameById(item.getSizeId()));
    itemRep.save(item);
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
    for (long cid : colorIds) {
      for (long sid : sizeIds) {
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
    // itemRep.save(item);
    // mcdRep.updateTime(item.getUpdatedAt(), item.getmId());
    return "redirect:modify";
  }

  // -------------------------taxon ----
  @RequestMapping("taxon2")
  @ResponseBody
  public List<Taxon> getTaxon2(Long id) {
    return TaxonRep.findByAncestry(id + "");
  }

  @RequestMapping("taxon3")
  @ResponseBody
  public List<Taxon> getTaxon3(Long id) {
    return TaxonRep.getTaxon3("%" + id);
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

  // 测试tinymce上传图片
  @RequestMapping("upload")
  @ResponseBody
  public String uploa(MultipartFile thumbnail) {
    // FileSource fileSource = new FileSource();
    // fileSource.setHost("192.168.200.71");
    // fileSource.setPort(8888);
    // try {
    // fileSource.startup();
    // FileTemplate template = new FileTemplate(fileSource.getConnection());
    // FileHandleStatus f =
    // template.saveFileByStream("sparrow/test.jpg", thumbnail.getInputStream());
    // System.out.println(f.getFileName());
    // } catch (IOException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // File file = new File(thumbnail.getOriginalFilename());
    boolean result = false;;
    try {
      result =
          Httptest.post(thumbnail.getBytes(), "http://192.168.200.71:8888/sparrow/test/shoes2.jpg");
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    if (result) {
      return "http://192.168.200.71:8888/sparrow/test/shoes2.jpg";
    }
    return "http://192.168.200.71:8888/areatrend_b/e0ca50c25f5c3b4957da6145ee3c9878.jpg?mode=2f&width=150&height=150";
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
    return "redirect:" + album.getMerchandiseId() + "/albumList";
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
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    if (result) {

      image.setUrl(url);
      imageRep.save(image);
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
  public String album(Merchandise merchandise) {
    merchandise.setOwnerId(getUserId());
    merchandise.setHolderId(getUserId());
    merchandise.setState("草稿");
    mcdRep.save(merchandise);
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
    // Merchandise merchandise = refinedService.toModify(id);
    // merchandise.setAlbums(albumRep.findByMerchandiseId(id));
    // model.addAttribute("brands", initService.getBrands());
    // model.addAttribute("genders", initService.getGenders());
    // model.addAttribute("topTaxons", initService.getTopTaxons());
    // model.addAttribute("merchandise", merchandise);
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



  // 保存基本信息
  @RequestMapping("{id}/basic/save")
  @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
  public String saveBasic(Merchandise merchandise) {
    saveMerchandise(merchandise, merchandise.getState());
    return "redirect:../modify";

  }

  private void saveMerchandise(Merchandise merchandise, String string) {
    merchandise.setHolderId(getUserId());
    merchandise.setOwnerId(getUserId());
    merchandise.setState(string);

    userRep.update(getUserId());
    mcdRep.save(merchandise);
    StateChange sc = new StateChange(merchandise.getId(), "", string, "新建精编");
    saveChange(sc);
  }

  // 保存修改精编-draft
  @RequestMapping("{id}/draft")
  @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
  public String draft(Merchandise merchandise) {
    mcdRep.updateSate(true, "草稿", merchandise.getId());

    return "redirect:../queryList";

  }

  // 保存修改精编-publish
  @RequestMapping("{id}/publish")
  @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
  public String publish(Merchandise merchandise) {
    mcdRep.updateSate(true, "待审核", merchandise.getId());
    return "redirect:../queryList";

  }

  // 跳转修改精编页
  @RequestMapping("{id}/modify")
  public String toModify2(Model model, @PathVariable Long id) {
    Merchandise merchandise = refinedService.toModify(id);
    merchandise.setAlbums(albumRep.findByMerchandiseId(id));
    // model.addAttribute("brands", initService.getBrands());
    // model.addAttribute("genders", initService.getGenders());
    model.addAttribute("topTaxons", initService.getRocDataList(CacheKey.TOPTAXONS));
    model.addAttribute("merchandise", merchandise);
    return BASE_PATH + "modify";
  }

  private void saveChange(StateChange sc) {
    sc.setCreatedUserId(getUserId());
    sc.setCreatedBy(getUserName());
    scRep.save(sc);
  }

}
