package com.atguigu.bookstore.service;

import com.atguigu.bookstore.dao.AccountDAO;
import com.atguigu.bookstore.dao.impl.AccountDAOImpl;
import com.atguigu.bookstore.domain.Account;

public class AccountService {

	private AccountDAO accountDAO = new AccountDAOImpl();
	
	public Account getAccount(int accountId){
		//����accountId��ȡ���
		return accountDAO.get(accountId);
	}
}
