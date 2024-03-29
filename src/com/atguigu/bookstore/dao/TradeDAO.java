package com.atguigu.bookstore.dao;

import java.util.Set;

import com.atguigu.bookstore.domain.Trade;

/**
 *2019年1月7日上午11:08:33
 *
 */
public interface TradeDAO {
	/**
	 * 向数据表中插入 Trade 对象
	 * @param trade
	 */
	public abstract void insert(Trade trade);
	/**
	 * 根据 userId 获取和其关联的 Trade 的集合
	 * @param userId
	 * @return
	 */
	public abstract Set<Trade> getTradesWithUserId(Integer userId);

}
