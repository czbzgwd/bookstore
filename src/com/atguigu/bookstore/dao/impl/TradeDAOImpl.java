package com.atguigu.bookstore.dao.impl;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import com.atguigu.bookstore.dao.TradeDAO;
import com.atguigu.bookstore.domain.Trade;

public class TradeDAOImpl extends BaseDAO<Trade> implements TradeDAO {

	/*@Override
	public void insert1(Trade trade) {
		String sql = "INSERT INTO trade (userid, tradetime) VALUES " +
				"(?, ?)";
		long tradeId = insert(sql, trade.getUserId(), trade.getTradeTime());
		trade.setTradeId((int)tradeId);
	}

	@Override
	public Set<Trade> getTradesWithUserId1(Integer userId) {
		String sql = "SELECT tradeId, userId, tradeTime FROM trade " +
				"WHERE userId = ? ORDER BY tradeTime DESC";
		return new LinkedHashSet(queryForList(sql, userId));
	}*/
	@Override
	public void insert(Trade trade) {
		String sql = "INSERT INTO bookstore.trade ("+
	                 "userid, tradetime) VALUES (?,?)";
		long tradeId = insert(sql,trade.getUserId(),trade.getTradeTime());
		trade.setTradeId((int)tradeId);

	}
	//¡î
	@Override
	public Set<Trade> getTradesWithUserId(Integer userId) {
		String sql = "select tradeid,userId,tradetime from trade where userid = ? order by tradeTime DESC";
		
		return new LinkedHashSet<Trade>(queryForList(sql,userId));
	}

}
