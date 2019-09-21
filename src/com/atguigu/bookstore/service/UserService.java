package com.atguigu.bookstore.service;

import com.atguigu.bookstore.dao.UserDAO;
import com.atguigu.bookstore.dao.impl.UserDAOImpl;
import com.atguigu.bookstore.domain.User;

//结账功能：根据用户名查询用户信息
public class UserService {

	//为什么要加private
	private UserDAO userDAO = new UserDAOImpl();
	
	public User getUserByUserName(String username){
		return userDAO.getUser(username);
	}
}
