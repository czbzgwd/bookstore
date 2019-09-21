package com.atguigu.bookstore.domain;

/**
 * 购物车中的商品以及该商品的数量
 * 
 * @author Administrator
 *
 */

public class ShoppingCartItem {

	private Book book;
	private int quantity;
	
	public ShoppingCartItem(Book book) {
		this.book = book;
		this.quantity = 1;
	}
	public Book getBook() {
		return book;
	}
	
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * 返回该商品的总钱数
	 */
	public float getItemMoney(){
		return book.getPrice()*quantity;
	}
	
	/*
	 * 使商品数量加1
	 */
	public void increment(){
		quantity++;
	}
}
