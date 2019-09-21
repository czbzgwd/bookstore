package com.atguigu.bookstore.service;

import com.atguigu.bookstore.dao.UserDAO;
import com.atguigu.bookstore.dao.impl.UserDAOImpl;
import com.atguigu.bookstore.domain.User;

//���˹��ܣ������û�����ѯ�û���Ϣ
public class UserService {

	//ΪʲôҪ��private
	private UserDAO userDAO = new UserDAOImpl();
	
	public User getUserByUserName(String username){
		return userDAO.getUser(username);
	}
}
