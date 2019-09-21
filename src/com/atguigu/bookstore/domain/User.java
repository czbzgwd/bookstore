package com.atguigu.bookstore.domain;

import java.util.LinkedHashSet;
import java.util.Set;

public class User {

	private Integer userid;
	private String userName;
	private int accountId;
	private Set<Trade> trades =new LinkedHashSet<Trade>();
	public User(Integer userid, String userName, int accountId) {
		super();
		this.userid = userid;
		this.userName = userName;
		this.accountId = accountId;
	}
	public User() {
		super();
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public Set<Trade> getTrades() {
		return trades;
	}
	public void setTrades(Set<Trade> trades) {
		this.trades = trades;
	}
	@Override
	public String toString() {
		return "User [userid=" + userid + ", userName=" + userName + ", accountId=" + accountId + "]";
	}
	
}
