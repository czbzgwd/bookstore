package com.atguigu.bookstore.dao;

import com.atguigu.bookstore.domain.User;

/**
 *2019年1月7日上午11:51:33
 *
 */
public interface UserDAO {
	/**
	 * 根据用户名获取 User 对象
	 * @param username
	 * @return
	 */
	public abstract User getUser(String userName);

}
