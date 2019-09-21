package com.atguigu.bookstore.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

import com.atguigu.bookstore.dao.BookDAO;
import com.atguigu.bookstore.dao.impl.BookDAOImpl;
import com.atguigu.bookstore.domain.Book;
import com.atguigu.bookstore.domain.ShoppingCartItem;
import com.atguigu.bookstore.web.CriteriaBook;
import com.atguigu.bookstore.web.Page;

public class BookDAOTest {

	private BookDAO bookDAO = new BookDAOImpl();
	@Test
	public void testGetBook() {
		Book book = bookDAO.getBook(5);
		System.out.println(book); 
		System.out.println(bookDAO.getBook(1));
	}

	@Test
	public void testGetPage() {
		CriteriaBook cb = new CriteriaBook(0,90,90);
		Page page = bookDAO.getPage(cb);
		System.out.println("pageNo: " + page.getPageNo());
		System.out.println("totalPageNumber: " + page.getTotalPageNumber());
		System.out.println("list: " + page.getList());
		System.out.println("prevPage: " + page.getPrevPage());
		System.out.println("nextPage: " + page.getNextPage()); 
	}

	@Test
	public void testGetTotalBookNumber() {
		CriteriaBook cb = new CriteriaBook(50,60,666);
		System.out.println(bookDAO.getTotalBookNumber(cb));;
	}

	@Test
	public void testGetPageList() {
		CriteriaBook cb = new CriteriaBook(0,90,6);
		List<Book> l = bookDAO.getPageList(cb, 3);
		for(Book b:l){
			System.out.println(b);
		}
	}

	@Test
	public void testGetStoreNumber() {
		System.out.println(bookDAO.getStoreNumber(2));
	}

	@Test
	public void testBatchUpdateStoreNumberAndSalesAmount() {
		Collection<ShoppingCartItem> items = new ArrayList<>();
		Book book = bookDAO.getBook(1);
		ShoppingCartItem sci = new ShoppingCartItem(book);
		sci.setQuantity(10);
		items.add(sci);
		
		book = bookDAO.getBook(2);
		sci = new ShoppingCartItem(book);
		sci.setQuantity(11);
		items.add(sci);
		
		book = bookDAO.getBook(3);
		sci = new ShoppingCartItem(book);
		sci.setQuantity(12);
		items.add(sci);
		
		book = bookDAO.getBook(4);
		sci = new ShoppingCartItem(book);
		sci.setQuantity(14);
		items.add(sci);
		
		bookDAO.batchUpdateStoreNumberAndSalesAmount(items);
	}

}
