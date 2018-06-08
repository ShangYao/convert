package com.jinanlongen.sparrow.domain.es;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import com.jinanlongen.sparrow.domain.Album;
import com.jinanlongen.sparrow.domain.Color;
import com.jinanlongen.sparrow.domain.Desc;
import com.jinanlongen.sparrow.domain.LineItem;
import com.jinanlongen.sparrow.domain.Merchandise;
import com.jinanlongen.sparrow.domain.Size;
import com.jinanlongen.sparrow.domain.SourceUrl;
import com.jinanlongen.sparrow.domain.Spec;
import com.jinanlongen.sparrow.util.StringUtils;

public class EsMerchandise {
  private String id;
  private String title;
  private String state;
  private String album_id;
  private long created_at;
  private long updated_at;



  private EsBrand brand;
  private EsTaxon taxon;
  private EsGender gender;
  private EsManager manager;
  private List<EsDesc> descs;
  private List<EsColor> colors;
  private List<EsSize> sizes;
  private List<String> tags;
  private List<EsAlbum> albums;
  private List<EsSku> skus;
  private List<EsUrl> urls;
  private List<EsSpec> specs;

  public EsMerchandise generate(Merchandise m) {
    this.id = m.getId() + "";
    this.title = m.getTitle();
    this.state = StringUtils.stateTransfor(m.getState());
    this.album_id = m.getAlbumId() == 0 ? null : (m.getAlbumId() + "");
    this.created_at = m.getCreatedAt().getTime();
    this.updated_at = m.getUpdatedAt().getTime();


    this.descs = generateDesc(m.getDescs());
    this.colors = generateColors(m.getColors());
    this.sizes = generateSizes(m.getSizes());
    this.skus = generateSkus(m.getLineItems());
    this.urls = generateUrls(m.getSourceUrl());
    this.specs = generateSpecs(m.getSpecs());
    this.albums = generateAlbums(m.getAlbums());

    this.manager = new EsManager().generate(m.getHolder());
    this.gender = new EsGender().generate(m.getGender());
    this.brand = new EsBrand().generate(m.getBrand());
    this.taxon = new EsTaxon().generate((m.getTaxon3() == null) ? m.getTaxon2() : m.getTaxon3());
    return this;
  }


  private List<EsAlbum> generateAlbums(List<Album> albums) {
    List<EsAlbum> esAlbums = new ArrayList<EsAlbum>();
    for (Album album : albums) {
      esAlbums.add(new EsAlbum().generate(album));
    }
    return esAlbums;
  }


  private List<EsSpec> generateSpecs(List<Spec> specs) {
    List<EsSpec> esSpecs = new ArrayList<EsSpec>();
    for (Spec spec : specs) {
      esSpecs.add(new EsSpec().generate(spec));
    }

    return esSpecs;
  }


  private List<EsUrl> generateUrls(List<SourceUrl> sourceUrls) {
    List<EsUrl> esUrls = new ArrayList<EsUrl>();
    for (SourceUrl sourceUrl : sourceUrls) {
      esUrls.add(new EsUrl().generate(sourceUrl));
    }
    return esUrls;
  }


  private List<EsSku> generateSkus(Set<LineItem> lineItems) {
    List<EsSku> skus = new ArrayList<EsSku>();
    for (LineItem lineItem : lineItems) {
      skus.add(new EsSku().generate(lineItem));
    }

    return skus;
  }


  private List<EsSize> generateSizes(List<Size> sizes) {
    List<EsSize> esSizes = new ArrayList<EsSize>();
    for (Size size : sizes) {
      esSizes.add(new EsSize().generate(size));
    }
    return esSizes;
  }


  private List<EsColor> generateColors(List<Color> colors) {
    List<EsColor> list = new ArrayList<EsColor>();
    EsColor esColor;
    for (Color color : colors) {
      esColor = new EsColor().generate(color);
      list.add(esColor);
    }
    return list;
  }


  private List<EsDesc> generateDesc(List<Desc> descs) {
    List<EsDesc> list = new ArrayList<EsDesc>();
    EsDesc esDesc;
    for (Desc desc : descs) {
      esDesc = new EsDesc().generate(desc);
      list.add(esDesc);
    }

    return list;
  }



  public EsBrand getBrand() {
    return brand;
  }


  public void setBrand(EsBrand brand) {
    this.brand = brand;
  }


  public EsTaxon getTaxon() {
    return taxon;
  }


  public void setTaxon(EsTaxon taxon) {
    this.taxon = taxon;
  }


  public EsGender getGender() {
    return gender;
  }


  public void setGender(EsGender gender) {
    this.gender = gender;
  }



  public List<EsDesc> getDescs() {
    return descs;
  }

  public List<EsDesc> getDescsJson() {

    return descs;
  }

  public void setDescs(List<EsDesc> descs) {
    this.descs = descs;
  }

  public List<EsColor> getColors() {
    return colors;
  }

  public void setColors(List<EsColor> colors) {
    this.colors = colors;
  }


  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }


  public String getId() {
    return id;
  }


  public void setId(String id) {
    this.id = id;
  }


  public String getAlbum_id() {
    return album_id;
  }


  public void setAlbum_id(String album_id) {
    this.album_id = album_id;
  }


  public long getCreated_at() {
    return created_at;
  }


  public void setCreated_at(long created_at) {
    this.created_at = created_at;
  }


  public long getUpdated_at() {
    return updated_at;
  }


  public void setUpdated_at(long updated_at) {
    this.updated_at = updated_at;
  }


  public EsManager getManager() {
    return manager;
  }


  public void setManager(EsManager manager) {
    this.manager = manager;
  }


  public List<EsSize> getSizes() {
    return sizes;
  }


  public void setSizes(List<EsSize> sizes) {
    this.sizes = sizes;
  }


  public List<String> getTags() {
    return tags;
  }


  public void setTags(List<String> tags) {
    this.tags = tags;
  }


  public List<EsAlbum> getAlbums() {
    return albums;
  }


  public void setAlbums(List<EsAlbum> albums) {
    this.albums = albums;
  }


  public List<EsSku> getSkus() {
    return skus;
  }


  public void setSkus(List<EsSku> skus) {
    this.skus = skus;
  }



  public List<EsUrl> getUrls() {
    return urls;
  }


  public void setUrls(List<EsUrl> urls) {
    this.urls = urls;
  }


  public List<EsSpec> getSpecs() {
    return specs;
  }


  public void setSpecs(List<EsSpec> specs) {
    this.specs = specs;
  }



}
