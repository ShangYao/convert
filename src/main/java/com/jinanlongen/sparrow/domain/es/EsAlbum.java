package com.jinanlongen.sparrow.domain.es;

import java.util.ArrayList;
import java.util.List;
import com.jinanlongen.sparrow.domain.Album;
import com.jinanlongen.sparrow.domain.Image;

public class EsAlbum {
  private String id;
  private String name;
  private List<EsImage> images;

  public EsAlbum generate(Album album) {
    this.id = album.getId() + "";
    this.name = album.getName();
    this.images = generateImages(album.getImages());
    return this;
  }

  private List<EsImage> generateImages(List<Image> images) {
    List<EsImage> esImages = new ArrayList<EsImage>();
    for (Image image : images) {
      esImages.add(new EsImage().generate(image));
    }

    return esImages;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<EsImage> getImages() {
    return images;
  }

  public void setImages(List<EsImage> images) {
    this.images = images;
  }



}
