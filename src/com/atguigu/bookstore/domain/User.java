package com.atguigu.bookstore.domain;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 *2019年1月7日上午11:53:34
 *
 */
public class User {

	private Integer userId;
	private String userName;
	private int accountId;
	//?
	private Set<Trade> trades = new LinkedHashSet<>();
	
	public User() {
		super();
	}

	/**
	 * @param userId
	 * @param userName
	 * @param accountId
	 * @param trades
	 */
	public User(Integer userId, String userName, int accountId, Set<Trade> trades) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.accountId = accountId;
		this.trades = trades;
	}

	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the accountId
	 */
	public int getAccountId() {
		return accountId;
	}

	/**
	 * @param accountId the accountId to set
	 */
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	/**
	 * @return the trades
	 */
	public Set<Trade> getTrades() {
		return trades;
	}

	/**
	 * @param trades the trades to set
	 */
	public void setTrades(Set<Trade> trades) {
		this.trades = trades;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", accountId=" + accountId + ", trades=" + trades
				+ "]";
	}
	
	
	
}
