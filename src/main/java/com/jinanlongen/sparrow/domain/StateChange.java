package com.jinanlongen.sparrow.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * 类说明
 * 
 * @author shangyao
 * @date 2018年1月23日
 */
@Entity(name = "state_changes")
@EntityListeners(AuditingEntityListener.class)
public class StateChange extends BaseDomain {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private long id;
	private long merchandiseId;
	private String oldState;
	private String newState;
	private long createdUserId;
	@CreatedDate
	private Date createdAt;
	private String createdBy;
	private String note;
	@Transient
	private String itemId;
	@Transient
	private String beginCreated;
	@Transient
	private String endCreated;
	@Transient
	private String mid;

	public String getMid() {

		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
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

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getMerchandiseId() {
		return merchandiseId;
	}

	public void setMerchandiseId(long merchandiseId) {
		this.merchandiseId = merchandiseId;
	}

	public String getOldState() {
		return oldState;
	}

	public void setOldState(String oldState) {
		this.oldState = oldState;
	}

	public String getNewState() {
		return newState;
	}

	public void setNewState(String newState) {
		this.newState = newState;
	}

	public long getCreatedUserId() {
		return createdUserId;
	}

	public void setCreatedUserId(long createdUserId) {
		this.createdUserId = createdUserId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
