package com.atguigu.bookstore.service;

import com.atguigu.bookstore.dao.BookDAO;
import com.atguigu.bookstore.dao.impl.BookDAOImpl;
import com.atguigu.bookstore.domain.Book;
import com.atguigu.bookstore.domain.ShoppingCart;
import com.atguigu.bookstore.web.CriteriaBook;
import com.atguigu.bookstore.web.Page;

public class BookService {

private BookDAO bookDAO = new BookDAOImpl();
private ShoppingCart sc = new ShoppingCart();	
	public Page<Book> getPage(CriteriaBook criteriaBook){
		return bookDAO.getPage(criteriaBook);
	}
	public Book getBook(int id){
		return bookDAO.getBook(id);
	}
	//���鼮��ӵ����ﳵ��
	public boolean addToCart(int id, ShoppingCart sc) {

		Book book = bookDAO.getBook(id);
		if(book != null){
			sc.addBook(book);
			return true;
		}else{
			return false;
		}
	}
	//��չ��ﳵ
	public void clearShoppingCart(ShoppingCart sc){
		sc.clear();
	}
	//ɾ��ָ����Ʒ
	public void removeItemFromShoppingCart(ShoppingCart sc,int id){
		
		sc.removeItem(id);
	}
	public void updateItemQuantity(ShoppingCart sc, int id, int quantity) {
		sc.updateItemQuantity(id, quantity);
		
	}
}
