package com.atguigu.bookstore.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.atguigu.bookstore.dao.Dao;
import com.atguigu.bookstore.db.JDBCUtils;
import com.atguigu.bookstore.utils.ReflectionUtils;
import com.mysql.jdbc.Statement;

public class BaseDAO<T> implements Dao<T> {

	// JavaWeb之DBUtils（一）QueryRunner类对数据表的增、删、查（8种结果集处理方式）、改操作
	private QueryRunner queryRunner = new QueryRunner();

	private Class<T> clazz;

	public BaseDAO() {
		clazz = ReflectionUtils.getSuperGenericType(getClass());
	}

	@Override
	public long insert(String sql, Object... args) {
		long id = 0;
		Connection connection = null;
		PreparedStatement preparedstatement = null;
		ResultSet resultSet = null;
		try {
			connection = JDBCUtils.getConnection();
			preparedstatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			// ?
			if (args != null) {
				for (int i = 0; i < args.length; i++) {
					// setObject用来给JDBC的SQL语句的占位符赋值的
					preparedstatement.setObject(i + 1, args[i]);
				}
			}
			preparedstatement.executeUpdate();
			resultSet = preparedstatement.getGeneratedKeys();
			// ?
			if (resultSet.next()) {
				id = resultSet.getLong(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.release(resultSet, preparedstatement);
			JDBCUtils.release(connection);
		}
		return id;
	}

	@Override
	public void update(String sql, Object... args) {
		Connection connection = null;
		try {

			connection = JDBCUtils.getConnection();
			queryRunner.update(connection, sql, args);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.release(connection);
		}

	}

	@Override
	public T query(String sql, Object... args) {
		Connection connection = null;
		try {

			connection = JDBCUtils.getConnection();
			//??
			return queryRunner.query(connection, sql, new BeanHandler<>(clazz), args);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.release(connection);
		}
		return null;
	}

	//?
	@Override
	public List<T> queryForList(String sql, Object... args) {
		Connection connection = null;
		
		try {
			 connection = JDBCUtils.getConnection();
			return queryRunner.query(connection, sql, new BeanListHandler<>(clazz), args);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}

	//获取某一个值
	//?
	@Override
	public <V> V getSingleVal(String sql, Object... args) {
		Connection connection = null;
		
		try {
			connection = JDBCUtils.getConnection();
			return (V)queryRunner.query(connection, sql, new ScalarHandler(), args);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	//批量
	//?
	@Override
	public void batch(String sql, Object[]... params) {
		Connection connection = null;
		
		try {
			connection = JDBCUtils.getConnection();
			queryRunner.batch(connection, sql, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
