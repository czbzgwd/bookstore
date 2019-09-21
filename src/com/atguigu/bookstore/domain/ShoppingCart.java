package com.atguigu.bookstore.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {

	//定义map是用于在购物车中删除商品
	private Map<Integer,ShoppingCartItem> books = new HashMap<>();
	
	//修改指定购物项的数量
	public void updateItemQuantity(Integer id,int quantity){
		ShoppingCartItem sci = books.get(id);
		if(sci != null){
			sci.setQuantity(quantity);
		}
	}
	//移除指定的购物项
	public void removeItem(Integer id){
		books.remove(id);
	}
	//清空购物车
	public void clear(){
		books.clear();
	}
	
	//返回购物车是否为空
	public boolean isEmpty(){
		return books.isEmpty();
	}
	//获取购物车中所有商品的总钱数
	public float getTotalMoney(){
		float total = 0;
		for(ShoppingCartItem sci:getItems()){
			total += sci.getItemMoney();
		}
		return total;
	}
	//获取购物车中所有ShoppingCartItem组成的集合
	public Collection<ShoppingCartItem> getItems(){
		return books.values();
	}
	
	//返回购物车中商品的总数量
	public int getBookNumber(){
		//return books.size();
		int total = 0;
		for(ShoppingCartItem sci:books.values()){
			total += sci.getQuantity();
		}
		return total;
	}
	
	public Map<Integer, ShoppingCartItem> getBooks() {
		return books;
	}
	//检验购物车中是否有指定id的商品
	public boolean hasBook(Integer id){
		
		return books.containsKey(id);
	}
	/**
	 * 向购物车中添加商品
	 */
	public void addBook(Book book){
	//1.判断购物车中是否有该商品，若有，使其数量加1，若没有，新创建其对应的ShoppingCartItem,并把其加入到books中。
		ShoppingCartItem sci = books.get(book.getId());
		
		if(sci == null){
			sci = new ShoppingCartItem(book);
			books.put(book.getId(), sci);
		}else{
			sci.increment();
		}
		
	}
}
