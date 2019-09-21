package com.atguigu.bookstore.dao.impl;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import com.atguigu.bookstore.dao.TradeDAO;
import com.atguigu.bookstore.domain.Trade;

public class TradeDAOImpl extends BaseDAO<Trade> implements TradeDAO{
	//插入交易记录
	@Override
	public void insert(Trade trade) {
		String sql = "insert into trade (userid,tradetime) values (?,?)";
		//update(sql,trade.getUserId(),new Date(new java.util.Date().getTime()));
		//update(sql,trade.getUserId(),trade.getTradeTime());
		/**
		 * long tradeId = insert(sql,trade.getTradeId(),trade.getTradeTime());
		 */
		long tradeId = insert(sql,trade.getUserId(),trade.getTradeTime());
		trade.setTradeId((int)tradeId);
	}
	

	//查询交易信息
	@Override
	public Set<Trade> getTradesWithUserId(Integer userId) {
		String sql = "select tradeid,userid,tradetime from trade where userid = ?";
		return new HashSet<>(queryForList(sql,userId));
	}

}
