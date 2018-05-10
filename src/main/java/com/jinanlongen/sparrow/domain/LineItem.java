package com.jinanlongen.sparrow.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.ElementCollection;
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
	private long mId;
	private String color;
	private String size;
	private float basePrice;
	private String salesState;
	private String rocidString;
	private int rocidCount;
	@ElementCollection
	private List<String> rocIds = new ArrayList<String>();
	@Transient
	private String[] rocid;
	@CreatedDate
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createdAt;
	@LastModifiedDate
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updatedAt;

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

	public String[] getRocid() {
		return rocid;
	}

	public void setRocid(String[] rocid) {
		this.rocid = rocid;
	}

	public int getRocidCount() {
		return rocidCount;
	}

	public void setRocidCount(int rocidCount) {
		this.rocidCount = rocidCount;
	}

	public String getRocidString() {
		return rocidString;
	}

	public void setRocidString(String rocidString) {
		this.rocidString = rocidString;
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

	public float getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(float basePrice) {
		this.basePrice = basePrice;
	}

	public String getSalesState() {
		return salesState;
	}

	public void setSalesState(String salesState) {
		this.salesState = salesState;
	}

	public List<String> getRocIds() {
		return rocIds;
	}

	public void setRocIds(List<String> rocIds) {
		this.rocIds = rocIds;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
