package com.atguigu.bookstore.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {

	//����map�������ڹ��ﳵ��ɾ����Ʒ
	private Map<Integer,ShoppingCartItem> books = new HashMap<>();
	
	//�޸�ָ�������������
	public void updateItemQuantity(Integer id,int quantity){
		ShoppingCartItem sci = books.get(id);
		if(sci != null){
			sci.setQuantity(quantity);
		}
	}
	//�Ƴ�ָ���Ĺ�����
	public void removeItem(Integer id){
		books.remove(id);
	}
	//��չ��ﳵ
	public void clear(){
		books.clear();
	}
	
	//���ع��ﳵ�Ƿ�Ϊ��
	public boolean isEmpty(){
		return books.isEmpty();
	}
	//��ȡ���ﳵ��������Ʒ����Ǯ��
	public float getTotalMoney(){
		float total = 0;
		for(ShoppingCartItem sci:getItems()){
			total += sci.getItemMoney();
		}
		return total;
	}
	//��ȡ���ﳵ������ShoppingCartItem��ɵļ���
	public Collection<ShoppingCartItem> getItems(){
		return books.values();
	}
	
	//���ع��ﳵ����Ʒ��������
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
	//���鹺�ﳵ���Ƿ���ָ��id����Ʒ
	public boolean hasBook(Integer id){
		
		return books.containsKey(id);
	}
	/**
	 * ���ﳵ�������Ʒ
	 */
	public void addBook(Book book){
	//1.�жϹ��ﳵ���Ƿ��и���Ʒ�����У�ʹ��������1����û�У��´������Ӧ��ShoppingCartItem,��������뵽books�С�
		ShoppingCartItem sci = books.get(book.getId());
		
		if(sci == null){
			sci = new ShoppingCartItem(book);
			books.put(book.getId(), sci);
		}else{
			sci.increment();
		}
		
	}
}
