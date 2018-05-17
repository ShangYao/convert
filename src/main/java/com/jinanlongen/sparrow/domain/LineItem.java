package com.jinanlongen.sparrow.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "line_items")
public class LineItem extends BaseDomain {
  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue
  private long id;
  @Column(nullable = false)
  private long mId;
  @Column(nullable = false)
  private long colorId;
  private String color;
  @Column(nullable = false)
  private long sizeId;
  private String size;
  private float price;
  private int stock;
  private long albumId;
  private String albumName;
  // private String salesState;
  // private String rocidString;
  // private int rocidCount;
  // @ElementCollection
  // private List<String> rocIds = new ArrayList<String>();
  @CreatedDate
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date createdAt;
  @LastModifiedDate
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date updatedAt;

  @Transient
  private long[] colors;
  @Transient
  private long[] sizes;



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



  public long getColorId() {
    return colorId;
  }

  public void setColorId(long colorId) {
    this.colorId = colorId;
  }

  public long getSizeId() {
    return sizeId;
  }

  public void setSizeId(long sizeId) {
    this.sizeId = sizeId;
  }

  public String getAlbumName() {
    return albumName;
  }

  public void setAlbumName(String albumName) {
    this.albumName = albumName;
  }

  public long getmId() {
    return mId;
  }

  public void setmId(long mId) {
    this.mId = mId;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public String getSize() {
    return size;
  }

  public void setSize(String size) {
    this.size = size;
  }



  public float getPrice() {
    return price;
  }

  public void setPrice(float price) {
    this.price = price;
  }

  public int getStock() {
    return stock;
  }

  public void setStock(int stock) {
    this.stock = stock;
  }

  public long getAlbumId() {
    return albumId;
  }

  public void setAlbumId(long albumId) {
    this.albumId = albumId;
  }



  public long[] getColors() {
    return colors;
  }

  public void setColors(long[] colors) {
    this.colors = colors;
  }

  public long[] getSizes() {
    return sizes;
  }

  public void setSizes(long[] sizes) {
    this.sizes = sizes;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }

}
