package com.jinanlongen.sparrow.domain;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.jinanlongen.sparrow.repository.AlbumRep;
import com.jinanlongen.sparrow.repository.ColorRep;
import com.jinanlongen.sparrow.repository.DescRep;
import com.jinanlongen.sparrow.repository.ImageRep;
import com.jinanlongen.sparrow.repository.LineItemRep;
import com.jinanlongen.sparrow.repository.SizeRep;
import com.jinanlongen.sparrow.repository.SourceUrlRep;
import com.jinanlongen.sparrow.repository.SpecRep;
import com.jinanlongen.sparrow.repository.UserRep;
import com.jinanlongen.sparrow.roc.domain.Brand;
import com.jinanlongen.sparrow.roc.domain.Gender;
import com.jinanlongen.sparrow.roc.domain.Taxon;
import com.jinanlongen.sparrow.roc.repository.BrandRep;
import com.jinanlongen.sparrow.roc.repository.GenderRep;
import com.jinanlongen.sparrow.roc.repository.TaxonRep;

@Component
public class EsMerchandise {
  @Autowired
  private BrandRep brandRep;
  @Autowired
  private GenderRep genderRep;
  @Autowired
  private TaxonRep taxonRep;
  @Autowired
  private AlbumRep albumRep;
  @Autowired
  private ImageRep imageRep;
  @Autowired
  private UserRep userRep;
  @Autowired
  private ColorRep colorRep;
  @Autowired
  private SizeRep sizeRep;
  @Autowired
  private SpecRep specRep;
  @Autowired
  private DescRep descRep;
  @Autowired
  private SourceUrlRep urlRep;
  @Autowired
  private LineItemRep itemRep;

  public EsMerchandise getEsMerchandise(Merchandise m) {
    this.id = m.getId();
    this.title = m.getTitle();
    this.state = m.getState();
    this.createdAt = m.getCreatedAt();
    this.updatedAt = m.getUpdatedAt();
    this.maintainAt = m.getMaintainAt();
    this.albumId = m.getAlbumId();

    this.brand = brandRep.findOne(m.getBrandId());
    this.albums = albumRep.findByMerchandiseId(m.getId());
    for (Album album : albums) {
      album.setImages(imageRep.findByAlbumId(album.getId()));
    }

    this.colors = colorRep.findByMerchandiseId(m.getId());
    this.sizes = sizeRep.findByMerchandiseId(m.getId());
    this.descs = descRep.findByMerchandiseId(m.getId());
    this.sourceUrls = urlRep.findByMerchandiseId(m.getId());
    this.specs = specRep.findByMerchandiseId(m.getId());
    this.items = itemRep.findByMId2(m.getId());

    this.gender = genderRep.findOne(m.getGenderId());
    this.taxon = taxonRep.findOne(m.getTaxonId());
    this.owner = userRep.findOne(m.getOwnerId());
    this.holder = userRep.findOne(m.getHolderId());
    this.reviewer = userRep.findOne(m.getReviewerId());
    // albums.stream().map(i->i.setImages(imageRep.findByAlbumId(i.getId()))
    return this;
  }

  private long id;
  private String title;
  private String state;
  private long albumId;
  private Date createdAt;
  private Date updatedAt;
  private Date maintainAt;

  private List<Album> albums;
  private List<Color> colors;
  private List<Size> sizes;
  private List<Desc> descs;
  private List<Spec> specs;
  private List<LineItem> items;
  private List<SourceUrl> sourceUrls;

  private Brand brand;
  private Gender gender;
  private Taxon taxon;

  private User owner;
  private User reviewer;
  private User holder;

  public List<Spec> getSpecs() {
    return specs;
  }

  public void setSpecs(List<Spec> specs) {
    this.specs = specs;
  }



  public List<Color> getColors() {
    return colors;
  }

  public void setColors(List<Color> colors) {
    this.colors = colors;
  }

  public List<Size> getSizes() {
    return sizes;
  }

  public void setSizes(List<Size> sizes) {
    this.sizes = sizes;
  }

  public List<LineItem> getItems() {
    return items;
  }

  public void setItems(List<LineItem> items) {
    this.items = items;
  }

  public List<SourceUrl> getSourceUrls() {
    return sourceUrls;
  }

  public void setSourceUrls(List<SourceUrl> sourceUrls) {
    this.sourceUrls = sourceUrls;
  }

  public List<Desc> getDescs() {
    return descs;
  }

  public void setDescs(List<Desc> descs) {
    this.descs = descs;
  }

  public long getId() {
    return id;
  }

  public long getAlbumId() {
    return albumId;
  }

  public void setAlbumId(long albumId) {
    this.albumId = albumId;
  }

  public List<Album> getAlbums() {
    return albums;
  }

  public void setAlbums(List<Album> albums) {
    this.albums = albums;
  }

  public void setId(long id) {
    this.id = id;
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

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  public Date getMaintainAt() {
    return maintainAt;
  }

  public void setMaintainAt(Date maintainAt) {
    this.maintainAt = maintainAt;
  }



  public Brand getBrand() {
    return brand;
  }

  public void setBrand(Brand brand) {
    this.brand = brand;
  }

  public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  public Taxon getTaxon() {
    return taxon;
  }

  public void setTaxon(Taxon taxon) {
    this.taxon = taxon;
  }

  public User getOwner() {
    return owner;
  }

  public void setOwner(User owner) {
    this.owner = owner;
  }

  public User getReviewer() {
    return reviewer;
  }

  public void setReviewer(User reviewer) {
    this.reviewer = reviewer;
  }

  public User getHolder() {
    return holder;
  }

  public void setHolder(User holder) {
    this.holder = holder;
  }



}
