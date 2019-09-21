package com.atguigu.bookstore.test;

import org.junit.Test;

import com.atguigu.bookstore.dao.UserDAO;
import com.atguigu.bookstore.dao.impl.UserDAOImpl;
import com.atguigu.bookstore.domain.User;

public class UserDAOTest {

	//为什么要加private
	private UserDAO userDAO = new UserDAOImpl();
	@Test
	public void testGetUser() {
	User u = userDAO.getUser("Tom");	
	System.out.println(u);
	}
	   

}
