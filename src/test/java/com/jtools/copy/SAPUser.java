package com.jtools.copy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SAPUser {
	private Long id;
	private String name;
	private String pwd;
	private Date createDate;
	private Date lastLoginDate;
	private SAPRole role;
	
	public SAPUser(String name, String pwd, Date createDate, Date lastLoginDate) {
		super();
		this.name = name;
		this.pwd = pwd;
		this.createDate = createDate;
		this.lastLoginDate = lastLoginDate;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getLastLoginDate() {
		return lastLoginDate;
	}
	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SAPRole getRole() {
		return role;
	}

	public void setRole(SAPRole role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "SAPUser [name=" + name + ", pwd=" + pwd + ", role=" + role + ", createDate=" + createDate
				+ ", lastLoginDate=" + lastLoginDate + "]";
	}
	
}
