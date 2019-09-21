package com.atguigu.bookstore.dao.impl;

import com.atguigu.bookstore.dao.AccountDAO;
import com.atguigu.bookstore.domain.Account;

public class AccountDAOImpl extends BaseDAO<Account> implements AccountDAO {

	@Override
	public Account get(Integer accountId) {
		String sql = "select balance from account where accountId = ?";
		return query(sql, accountId);
	}

	//¡î   balance = balance - ?
	@Override
	public void updateBalance(Integer accountId, float amount) {
		String sql = "update account set balance = balance - ? where accountId = ?";
		update(sql,amount, accountId);
	}

}
