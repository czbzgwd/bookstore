package com.atguigu.bookstore.dao;

import java.util.Collection;
import java.util.Set;

import com.atguigu.bookstore.domain.TradeItem;

/**
 *2019年1月7日上午11:42:33
 *
 */
public interface TradeItemDAO {
	/**
	 * 批量保存 TradeItem 对象
	 * @param items
	 */
	public abstract void batchSave(Collection<TradeItem> items);
	/**
	 * 根据 tradeId 获取和其关联的 TradeItem 的集合
	 * @param tradeId
	 * @return
	 */
	public abstract Set<TradeItem> getTradeItemsWithTradeId(Integer tradeId);

}
