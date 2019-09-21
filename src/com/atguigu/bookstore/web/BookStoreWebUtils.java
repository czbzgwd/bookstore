package com.atguigu.bookstore.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.atguigu.bookstore.domain.ShoppingCart;

public class BookStoreWebUtils {

	/*
	 * 获取购物车对象：从session中获取，如果没有那么就创建一个对象再放入session中，若有则直接返回。
	 */
	public static ShoppingCart getShoppingCart(HttpServletRequest request){
		HttpSession session = request.getSession();
		ShoppingCart sc = (ShoppingCart)session.getAttribute("ShoppingCart");
		if(sc == null){
			sc = new ShoppingCart();
			session.setAttribute("ShoppingCart", sc);
		}
		return sc;
	}
}
