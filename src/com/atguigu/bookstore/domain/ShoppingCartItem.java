package com.atguigu.bookstore.domain;

/**
 *2019年1月7日上午10:30:17
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
	 * 返回该商品在购物车中的钱数
	 * @return
	 */
	public float getItemPrice(){
		return book.getPrice()*quantity;
	}
	/**
	 * 使商品数量 + 1
	 */
	public void increment(){
		 quantity++;
	}
	
}
