package com.atguigu.bookstore.domain;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

public class Trade {

	private Integer tradeId;
	private Date tradeTime;
	//Trade�����Ķ��TradeItem
	private Set<TradeItem> items = new LinkedHashSet<>();
	//�� Trade ������ User �� userId
	private Integer userId;
	public Integer getTradeId() {
		return tradeId;
	}
	public void setTradeId(Integer tradeId) {
		this.tradeId = tradeId;
	}
	public Date getTradeTime() {
		return tradeTime;
	}
	public void setTradeTime(Date tradeTime) {
		this.tradeTime = tradeTime;
	}
	public Set<TradeItem> getTradeitems() {
		return items;
	}
	public void setTradeitems(Set<TradeItem> tradeitems) {
		this.items = tradeitems;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "Trade [tradeId=" + tradeId + ", tradeTime=" + tradeTime + " userId="
				+ userId + "]";
	}
	
}
