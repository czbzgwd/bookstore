package com.atguigu.bookstore.service;

import com.atguigu.bookstore.dao.BookDAO;
import com.atguigu.bookstore.dao.impl.BookDAOImpl;
import com.atguigu.bookstore.domain.Book;
import com.atguigu.bookstore.domain.ShoppingCart;
import com.atguigu.bookstore.web.CriteriaBook;
import com.atguigu.bookstore.web.Page;

public class BookService {

private BookDAO bookDAO = new BookDAOImpl();
	
	public Page<Book> getPage(CriteriaBook criteriaBook){
		return bookDAO.getPage(criteriaBook);
	}
	public Book getBook(int id){
		return bookDAO.getBook(id);
	}
	
	public boolean addToCart(int id, ShoppingCart sc) {

		Book book = bookDAO.getBook(id);
		if(book != null){
			sc.addBook(book);
			return true;
		}else{
			return false;
		}
	}
}
