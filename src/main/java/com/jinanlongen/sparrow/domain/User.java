package com.jinanlongen.sparrow.domain;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
public class User extends BaseDomain implements UserDetails {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private long id;
	private String userName;
	private String code;

	@Transient
	private Long roleId[];
	@Transient
	private List<? extends GrantedAuthority> authorities;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "UsersRoles", joinColumns = { @JoinColumn(name = "uid") }, inverseJoinColumns = {
			@JoinColumn(name = "roleId") })
	private Set<Role> roleList;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "GroupsUsers", joinColumns = { @JoinColumn(name = "uid") }, inverseJoinColumns = {
			@JoinColumn(name = "groupId") })
	private Set<Group> groupList;

	private String groupNameString;
	@Transient
	private String groupIdString;
	private String roleNameString;
	@Transient
	private String roleIdString;
	@Column(nullable = true)
	private boolean hasRefined;
	@Transient
	private boolean selected;

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isHasRefined() {
		return hasRefined;
	}

	public void setHasRefined(boolean hasRefined) {
		this.hasRefined = hasRefined;
	}

	public String getGroupNameString() {
		return groupNameString;
	}

	public void setGroupNameString(String groupNameString) {
		this.groupNameString = groupNameString;
	}

	public String getGroupIdString() {
		return groupIdString;
	}

	public void setGroupIdString(String groupIdString) {
		this.groupIdString = groupIdString;
	}

	public String getRoleIdString() {
		return roleIdString;
	}

	public void setRoleIdString(String roleIdString) {
		this.roleIdString = roleIdString;
	}

	public String getRoleNameString() {
		return roleNameString;
	}

	public void setRoleNameString(String roleNameString) {
		this.roleNameString = roleNameString;
	}

	public Set<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(Set<Role> roleList) {
		this.roleList = roleList;
	}

	public Set<Group> getGroupList() {
		return groupList;
	}

	public void setGroupList(Set<Group> groupList) {
		this.groupList = groupList;
	}

	public Long[] getRoleId() {
		return roleId;
	}

	public void setRoleId(Long[] roleId) {
		this.roleId = roleId;
	}

	public String getUserName() {
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public void setGrantedAuthorities(List<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setAuthorities(List<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public String getUsername() {
		return userName;
	}

	@Override
	public String getPassword() {
		return null;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}