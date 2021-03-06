package com.jinanlongen.sparrow.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;
import com.jinanlongen.sparrow.roc.domain.Brand;
import com.jinanlongen.sparrow.roc.domain.Gender;
import com.jinanlongen.sparrow.roc.domain.Taxon;
import com.jinanlongen.sparrow.util.PageableUtils;

/**
 * 类说明
 * 
 * @author shangyao
 * @date 2018年1月23日
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "merchandises")
public class Merchandise extends BaseDomain implements Serializable {
  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "m_seq")
  @SequenceGenerator(name = "m_seq", sequenceName = "mer_seq", allocationSize = 1)
  private long id;
  private String state;
  private String title;
  @Column(nullable = false)
  private long ownerId;
  private long reviewerId;
  @Column(nullable = false)
  private long holderId;
  private int declinedCount;
  private boolean reviewNeeded = true;
  @CreatedDate
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date createdAt;
  @LastModifiedDate
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date updatedAt;
  private Date maintainAt;
  // @Column(nullable = true)
  private long albumId;// 商品相册
  private String brandId;
  private String genderId;
  private String taxonId;
  private String ownerName;
  private String reviewerName;
  private String holderName;
  @Transient
  private int redirectPage;
  @Transient
  private User holder;
  @Transient
  private Taxon taxon1;
  @Transient
  private Taxon taxon2;
  @Transient
  private Taxon taxon3;
  @Transient
  private String taxonId3;
  @Transient
  private String taxonId2;
  @Transient
  private Brand brand;
  @Transient
  private Gender gender;
  @Transient
  private List<SourceUrl> sourceUrl = new ArrayList<SourceUrl>();
  @Transient
  private List<Album> albums = new ArrayList<Album>();
  @Transient
  private Set<LineItem> lineItems;
  @Transient
  private List<Color> colors;
  @Transient
  private List<Size> sizes;
  @Transient
  private List<Spec> specs;
  @Transient
  private List<Desc> descs;
  @Transient
  private List<StateChange> stateChanges;



  @Transient
  private PageableUtils draftPage = new PageableUtils();
  @Transient
  private PageableUtils committedPage = new PageableUtils();
  @Transient
  private PageableUtils declinedPage = new PageableUtils();
  @Transient
  private PageableUtils retiredPage = new PageableUtils();
  @Transient
  private PageableUtils publishedPage = new PageableUtils();
  @Transient
  private PageableUtils trashPage = new PageableUtils();
  @Transient
  private String tabShow;
  @Transient
  private int draftPageNum;
  @Transient
  private int committedPageNum;
  @Transient
  private int declinedPageNum;
  @Transient
  private int retiredPageNum;
  @Transient
  private int publishedPageNum;
  @Transient
  private int trashPageNum;
  @Transient
  private String beginCreated;
  @Transient
  private String endCreated;
  @Transient
  private String beginUpdated;
  @Transient
  private String endUpdated;
  @Transient
  private String beginMaintain;
  @Transient
  private String endMaintain;
  @Transient
  private String queryString;

  @Transient
  private List<Statistics> userSlist;
  @Transient
  private List<Statistics> aSlist;
  @Transient
  private List<Statistics> personalMaintains;
  @Transient
  private List<Statistics> storeMaintains;

  private String declinedReason;

  @Transient
  private long oldId;



  public List<Statistics> getPersonalMaintains() {
    return personalMaintains;
  }

  public void setPersonalMaintains(List<Statistics> personalMaintains) {
    this.personalMaintains = personalMaintains;
  }

  public List<Statistics> getStoreMaintains() {
    return storeMaintains;
  }

  public void setStoreMaintains(List<Statistics> storeMaintains) {
    this.storeMaintains = storeMaintains;
  }

  public User getHolder() {
    return holder;
  }

  public void setHolder(User holder) {
    this.holder = holder;
  }

  public List<StateChange> getStateChanges() {
    return stateChanges;
  }

  public void setStateChanges(List<StateChange> stateChanges) {
    this.stateChanges = stateChanges;
  }



  public String getTaxonId3() {
    return taxonId3;
  }

  public void setTaxonId3(String taxonId3) {
    this.taxonId3 = taxonId3;
  }

  public String getTaxonId2() {
    return taxonId2;
  }

  public void setTaxonId2(String taxonId2) {
    this.taxonId2 = taxonId2;
  }

  public String getOwnerName() {
    return ownerName;
  }

  public void setOwnerName(String ownerName) {
    this.ownerName = ownerName;
  }

  public String getReviewerName() {
    return reviewerName;
  }

  public void setReviewerName(String reviewerName) {
    this.reviewerName = reviewerName;
  }

  public String getHolderName() {
    return holderName;
  }

  public void setHolderName(String holderName) {
    this.holderName = holderName;
  }

  public List<Desc> getDescs() {
    return descs;
  }

  public void setDescs(List<Desc> descs) {
    this.descs = descs;
  }

  public List<Spec> getSpecs() {
    return specs;
  }

  public int getRedirectPage() {
    return redirectPage;
  }

  public void setRedirectPage(int redirectPage) {
    this.redirectPage = redirectPage;
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

  public Taxon getTaxon1() {
    return taxon1;
  }

  public void setTaxon1(Taxon taxon1) {
    this.taxon1 = taxon1;
  }

  public Taxon getTaxon2() {
    return taxon2;
  }

  public void setTaxon2(Taxon taxon2) {
    this.taxon2 = taxon2;
  }

  public Taxon getTaxon3() {
    return taxon3;
  }

  public void setTaxon3(Taxon taxon3) {
    this.taxon3 = taxon3;
  }


  public String getBrandId() {
    return brandId;
  }

  public void setBrandId(String brandId) {
    this.brandId = brandId;
  }

  public String getGenderId() {
    return genderId;
  }

  public void setGenderId(String genderId) {
    this.genderId = genderId;
  }

  public String getTaxonId() {
    return taxonId;
  }

  public void setTaxonId(String taxonId) {
    this.taxonId = taxonId;
  }

  public long getOldId() {
    return oldId;
  }

  public void setOldId(long oldId) {
    this.oldId = oldId;
  }

  public List<SourceUrl> getSourceUrl() {
    return sourceUrl;
  }

  public void setSourceUrl(List<SourceUrl> sourceUrl) {
    this.sourceUrl = sourceUrl;
  }



  public long getAlbumId() {
    return albumId;
  }

  public void setAlbumId(long albumId) {
    this.albumId = albumId;
  }



  private String oldMerchandiseid;

  public String getOldMerchandiseid() {
    return oldMerchandiseid;
  }

  public void setOldMerchandiseid(String oldMerchandiseid) {
    this.oldMerchandiseid = oldMerchandiseid;
  }



  public String getBeginMaintain() {
    return beginMaintain;
  }

  public void setBeginMaintain(String beginMaintain) {
    this.beginMaintain = beginMaintain;
  }

  public String getEndMaintain() {
    return endMaintain;
  }

  public void setEndMaintain(String endMaintain) {
    this.endMaintain = endMaintain;
  }

  public Date getMaintainAt() {
    return maintainAt;
  }

  public void setMaintainAt(Date maintainAt) {
    this.maintainAt = maintainAt;
  }

  public Set<LineItem> getLineItems() {
    return lineItems;
  }

  public void setLineItems(Set<LineItem> lineItems) {
    this.lineItems = lineItems;
  }

  public String getDeclinedReason() {
    return declinedReason;
  }

  public void setDeclinedReason(String declinedReason) {
    this.declinedReason = declinedReason;
  }



  public List<Album> getAlbums() {
    return albums;
  }

  public void setAlbums(List<Album> albums) {
    this.albums = albums;
  }



  public List<Statistics> getUserSlist() {
    return userSlist;
  }

  public void setUserSlist(List<Statistics> userSlist) {
    this.userSlist = userSlist;
  }


  public List<Statistics> getaSlist() {
    return aSlist;
  }

  public void setaSlist(List<Statistics> aSlist) {
    this.aSlist = aSlist;
  }

  public boolean isReviewNeeded() {
    return reviewNeeded;
  }

  public void setReviewNeeded(boolean reviewNeeded) {
    this.reviewNeeded = reviewNeeded;
  }

  public boolean getReviewNeeded() {
    return reviewNeeded;
  }


  public int getDeclinedCount() {
    return declinedCount;
  }

  public void setDeclinedCount(int declinedCount) {
    this.declinedCount = declinedCount;
  }



  public String getQueryString() {
    return queryString;
  }

  public void setQueryString(String queryString) {
    this.queryString = queryString;
  }

  public String getBeginUpdated() {
    return beginUpdated;
  }

  public void setBeginUpdated(String beginUpdated) {
    this.beginUpdated = beginUpdated;
  }

  public String getEndUpdated() {
    return endUpdated;
  }

  public void setEndUpdated(String endUpdated) {
    this.endUpdated = endUpdated;
  }

  public String getBeginCreated() {
    return beginCreated;
  }

  public void setBeginCreated(String beginCreated) {
    this.beginCreated = beginCreated;
  }

  public String getEndCreated() {
    return endCreated;
  }

  public void setEndCreated(String endCreated) {
    this.endCreated = endCreated;
  }

  public int getDraftPageNum() {
    return draftPageNum;
  }

  public void setDraftPageNum(int draftPageNum) {
    this.draftPageNum = draftPageNum;
  }

  public int getCommittedPageNum() {
    return committedPageNum;
  }

  public void setCommittedPageNum(int committedPageNum) {
    this.committedPageNum = committedPageNum;
  }

  public int getDeclinedPageNum() {
    return declinedPageNum;
  }

  public void setDeclinedPageNum(int declinedPageNum) {
    this.declinedPageNum = declinedPageNum;
  }

  public int getRetiredPageNum() {
    return retiredPageNum;
  }

  public void setRetiredPageNum(int retiredPageNum) {
    this.retiredPageNum = retiredPageNum;
  }

  public PageableUtils getPublishedPage() {
    return publishedPage;
  }

  public void setPublishedPage(PageableUtils publishedPage) {
    this.publishedPage = publishedPage;
  }

  public int getPublishedPageNum() {
    return publishedPageNum;
  }

  public void setPublishedPageNum(int publishedPageNum) {
    this.publishedPageNum = publishedPageNum;
  }

  public int getTrashPageNum() {
    return trashPageNum;
  }

  public void setTrashPageNum(int trashPageNum) {
    this.trashPageNum = trashPageNum;
  }

  public String getTabShow() {
    return tabShow == null ? "" : tabShow;
  }

  public void setTabShow(String tabShow) {
    this.tabShow = tabShow;
  }

  public PageableUtils getDraftPage() {
    return draftPage;
  }

  public void setDraftPage(PageableUtils draftPage) {
    this.draftPage = draftPage;
  }

  public PageableUtils getCommittedPage() {
    return committedPage;
  }

  public void setCommittedPage(PageableUtils committedPage) {
    this.committedPage = committedPage;
  }

  public PageableUtils getDeclinedPage() {
    return declinedPage;
  }

  public void setDeclinedPage(PageableUtils declinedPage) {
    this.declinedPage = declinedPage;
  }

  public PageableUtils getRetiredPage() {
    return retiredPage;
  }

  public void setRetiredPage(PageableUtils retiredPage) {
    this.retiredPage = retiredPage;
  }

  public PageableUtils getTrashPage() {
    return trashPage;
  }

  public void setTrashPage(PageableUtils trashPage) {
    this.trashPage = trashPage;
  }



  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }



  public long getHolderId() {
    return holderId;
  }

  public void setHolderId(long holderId) {
    this.holderId = holderId;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }



  public String getTitle() {
    return title == null ? "" : title.trim();
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public long getOwnerId() {
    return ownerId;
  }

  public void setOwnerId(long ownerId) {
    this.ownerId = ownerId;
  }

  public long getReviewerId() {
    return reviewerId;
  }

  public void setReviewerId(long reviewerId) {
    this.reviewerId = reviewerId;
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

  public static long getSerialversionuid() {
    return serialVersionUID;
  }

}
