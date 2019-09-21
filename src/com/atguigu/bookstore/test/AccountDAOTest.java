package com.atguigu.bookstore.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.atguigu.bookstore.dao.AccountDAO;
import com.atguigu.bookstore.dao.impl.AccountDAOImpl;
import com.atguigu.bookstore.domain.Account;

public class AccountDAOTest {

	AccountDAO accountDAO = new AccountDAOImpl();
	@Test
	public void testGet() {
		Account account = accountDAO.get(1);
		System.out.println(account.getBalance());
	}

	@Test
	public void testUpdateBalance() {
		accountDAO.updateBalance(1,400);;
	}

}
