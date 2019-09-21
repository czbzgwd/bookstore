package com.atguigu.bookstore.test;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.List;

import org.junit.Test;

import com.atguigu.bookstore.dao.impl.BaseDAO;
import com.atguigu.bookstore.dao.impl.BookDAOImpl;
import com.atguigu.bookstore.domain.Book;
import com.atguigu.bookstore.domain.Trade;

/**
 *2019年1月7日下午3:53:12
 *
 */
public class BaseDAOTest {
    private BookDAOImpl bookDAOImpl = new BookDAOImpl();
	@Test
	public void testInsert() {
		String sql = "insert into trade (userid,tradetime) values (?,?)";
	    long id = bookDAOImpl.insert(sql, 1,new Date(new java.util.Date().getTime()));
	    System.out.println(id);
	}

	/**
	 * Test method for {@link com.atguigu.bookstore.dao.impl.BaseDAO#update(java.lang.String, java.lang.Object[])}.
	 */
	@Test
	public void testUpdate() {
		String sql = "update mybooks set Salesamount = ? where id = ?";
		bookDAOImpl.update(sql, 10, 4);
	}

	
	@Test
	public void testQuery() {
		String sql = "select id,author,title,price,publishingDate,salesAmount,storeNumber,remark"
				+ " from mybooks  where id = ?";
		Book book = bookDAOImpl.query(sql, 4);
		
		System.out.println(book);
	}

	/**
	 * Test method for {@link com.atguigu.bookstore.dao.impl.BaseDAO#queryForList(java.lang.String, java.lang.Object[])}.
	 */
	@Test
	public void testQueryForList() {
		String sql = "select id,author,title,price,publishingDate,salesAmount,storeNumber,remark"
				+ " from mybooks  where price between  ? and ?";
		List<Book> books = bookDAOImpl.queryForList(sql,50,60);
		for (Book book : books){
			System.out.println(book);
		}
		//System.out.println(books);
	}

	@Test
	public void testGetSingleVal() {
		String sql = "select count(id) from mybooks";
		Long count = bookDAOImpl.getSingleVal(sql);
		System.out.println(count);
		
	}

	@Test
	public void testBatch() {
		String sql = "update  mybooks set publishingdate = ? " +
				"where id = ?";
		 
		bookDAOImpl.batch(sql, new Object[]{"2010-3-16",15},new Object[]{"2011-8-12",16},new Object[]{"2019-8-25",17});
	}

}
