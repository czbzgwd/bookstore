package com.atguigu.bookstore.dao.impl;

import java.util.Collection;
import java.util.List;

import com.atguigu.bookstore.dao.BookDAO;
import com.atguigu.bookstore.domain.Book;
import com.atguigu.bookstore.domain.ShoppingCartItem;
import com.atguigu.bookstore.web.CriteriaBook;
import com.atguigu.bookstore.web.Page;

public class BookDAOImpl extends BaseDAO<Book> implements BookDAO {

	// 获取书的详细信息
	@Override
	public Book getBook(int id) {
		String sql = "select id,author,title,price,publishingDate,salesAmount,storeNumber,remark"
				+ " from mybooks  where id = ? ";
		return query(sql, id);
	}

	//☆该方法较难理解
	//3
	@Override
	public Page<Book> getPage(CriteriaBook cb) {
		Page page = new Page(cb.getPageNo());
		page.setTotalItemNumber(getTotalBookNumber(cb));
		//校验pageNo的合法性☆
		cb.setPageNo(page.getPageNo());
		page.setList(getPageList(cb,3));
		return page;
	}

	//1
	// 获取书的数量
	@Override
	public long getTotalBookNumber(CriteriaBook cb) {
		String sql = "select count(id) from mybooks where price >= ? and price <=?";
		return getSingleVal(sql, cb.getMinPrice(),cb.getMaxPrice());
	}

	//2
	@Override
	public List<Book> getPageList(CriteriaBook cb, int pageSize) {
		/*String sql = "SELECT id, author, title, price, publishingDate, " +
				"salesAmount, storeNumber, remark FROM mybooks " +
				"WHERE price >= ? AND price <= ? " +
				"LIMIT ?, ?";*/
		String sql = "select id,author,title,price,publishingDate,salesAmount,storeNumber,remark"
				+ " from mybooks  where price >= ? and price <=?  limit ?,?";
		return queryForList(sql, cb.getMinPrice(),cb.getMaxPrice(),(cb.getPageNo()-1)*pageSize,pageSize);
	}

	@Override
	public int getStoreNumber(Integer id) {
		String sql = "select storeNumber from mybooks  where id = ? ";
		//int storeNumber = query(sql, id).getStoreNumber();
		return getSingleVal(sql, id);
	}

	@Override
	public void batchUpdateStoreNumberAndSalesAmount(Collection<ShoppingCartItem> items) {
		// TODO Auto-generated method stub
		
	}

	/*@Override
	public void batchUpdateStoreNumberAndSalesAmount(Collection<ShoppingCartItem> items) {
		// TODO Auto-generated method stub

	}*/

}
