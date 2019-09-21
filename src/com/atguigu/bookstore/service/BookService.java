package com.atguigu.bookstore.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

import com.atguigu.bookstore.dao.AccountDAO;
import com.atguigu.bookstore.dao.BookDAO;
import com.atguigu.bookstore.dao.TradeDAO;
import com.atguigu.bookstore.dao.TradeItemDAO;
import com.atguigu.bookstore.dao.UserDAO;
import com.atguigu.bookstore.dao.impl.AccountDAOImpl;
import com.atguigu.bookstore.dao.impl.BookDAOImpl;
import com.atguigu.bookstore.dao.impl.TradeDAOImpl;
import com.atguigu.bookstore.dao.impl.TradeItemDAOImpl;
import com.atguigu.bookstore.dao.impl.UserDAOImpl;
import com.atguigu.bookstore.domain.Book;
import com.atguigu.bookstore.domain.ShoppingCart;
import com.atguigu.bookstore.domain.ShoppingCartItem;
import com.atguigu.bookstore.domain.Trade;
import com.atguigu.bookstore.domain.TradeItem;
import com.atguigu.bookstore.web.CriteriaBook;
import com.atguigu.bookstore.web.Page;

public class BookService {

private BookDAO bookDAO = new BookDAOImpl();
private ShoppingCart sc = new ShoppingCart();	
	public Page<Book> getPage(CriteriaBook criteriaBook){
		return bookDAO.getPage(criteriaBook);
	}
	public Book getBook(int id){
		return bookDAO.getBook(id);
	}
	//将书籍添加到购物车中
	public boolean addToCart(int id, ShoppingCart sc) {

		Book book = bookDAO.getBook(id);
		if(book != null){
			sc.addBook(book);
			return true;
		}else{
			return false;
		}
	}
	//清空购物车
	public void clearShoppingCart(ShoppingCart sc){
		sc.clear();
	}
	//删除指定商品
	public void removeItemFromShoppingCart(ShoppingCart sc,int id){
		
		sc.removeItem(id);
	}
	public void updateItemQuantity(ShoppingCart sc, int id, int quantity) {
		sc.updateItemQuantity(id, quantity);
		
	}
	
	private AccountDAO accountDAO = new AccountDAOImpl();
	private TradeDAO tradeDAO = new TradeDAOImpl();
	private UserDAO userDAO = new UserDAOImpl();
	private TradeItemDAO tradeItemDAO = new TradeItemDAOImpl();
	//结账
	//业务方法：调用了多个DAO方法，构成一个业务。
	public void cash(ShoppingCart shoppingCart, String username, String accountId) {
	    //1、更新数据表mybooks相关的salesamount和storenumber
		bookDAO.batchUpdateStoreNumberAndSalesAmount(shoppingCart.getItems());
		//int i = 10/0;
		//2、更新account数据表的balance
		accountDAO.updateBalance(Integer.parseInt(accountId), shoppingCart.getTotalMoney());
		//3、向trade数据表中插入一条数据
		Trade trade = new Trade();
		//引入的是sql下的Date()
		trade.setTradeTime(new Date(new java.util.Date().getTime()));
		trade.setUserId(userDAO.getUser(username).getUserid());
		tradeDAO.insert(trade);
		//4、向tradeitem中插入n条记录
		Collection<TradeItem> items = new ArrayList<>();
		for(ShoppingCartItem sci:shoppingCart.getItems()){
			TradeItem tradeItem = new TradeItem();
			tradeItem.setBookId(sci.getBook().getId());
			tradeItem.setQuantity(sci.getQuantity());
			//tradeDAO.insert(trade);该方法已经赋值了tradeid
			tradeItem.setTradeId(trade.getTradeId());
			items.add(tradeItem);
		}
		tradeItemDAO.batchSave(items);
		//5、清空购物车
		shoppingCart.clear();
	}
}
