package com.atguigu.bookstore.service;

import java.util.Iterator;
import java.util.Set;

import com.atguigu.bookstore.dao.BookDAO;
import com.atguigu.bookstore.dao.TradeDAO;
import com.atguigu.bookstore.dao.TradeItemDAO;
import com.atguigu.bookstore.dao.UserDAO;
import com.atguigu.bookstore.dao.impl.BookDAOImpl;
import com.atguigu.bookstore.dao.impl.TradeDAOImpl;
import com.atguigu.bookstore.dao.impl.TradeItemDAOImpl;
import com.atguigu.bookstore.dao.impl.UserDAOImpl;
import com.atguigu.bookstore.domain.Trade;
import com.atguigu.bookstore.domain.TradeItem;
import com.atguigu.bookstore.domain.User;

//���˹��ܣ������û�����ѯ�û���Ϣ
public class UserService {

	//ΪʲôҪ��private
	private UserDAO userDAO = new UserDAOImpl();
	private TradeDAO tradeDAO = new TradeDAOImpl();
	private TradeItemDAO tradeItemDAO = new TradeItemDAOImpl();
	private BookDAO bookDAO = new BookDAOImpl();
	public User getUserByUserName(String username){
		return userDAO.getUser(username);
	}
	
	public User getUserWithTrades(String username){
	//1������userDAO�ķ�����ȡuser����
		User user = userDAO.getUser(username);
		if(user == null){
			return null;
		}
    //2������TradeDAO������ȡtrade���ϣ�����װ��ΪUser���ԡ�
		int userId = user.getUserId();
	//3������TradeItemDAO�ķ�����ȡÿһ��Trade�е�TradeItem���ϣ�������װ��Ϊtrade����
		Set<Trade> trades = tradeDAO.getTradesWithUserId(userId);
		if(trades != null){
			for(Trade trade:trades){
				int tradeId = trade.getTradeId();
				Set<TradeItem> items = tradeItemDAO.getTradeItemsWithTradeId(tradeId);
			   
				if(items != null){
					for(TradeItem item:items){
					item.setBook(bookDAO.getBook(item.getBookId()));	
					}
					trade.setItems(items);
				}
			}
		}
		user.setTrades(trades);
		
		return user;
	}


	/*public User getUserWithTrades(String username){
		
//		���� UserDAO �ķ�����ȡ User ����
		User user = userDAO.getUser(username);
		if(user == null){
			return null;
		}
		
//		���� TradeDAO �ķ�����ȡ Trade �ļ��ϣ�����װ��Ϊ User ������
		int userId = user.getUserId();
		
//		���� TradeItemDAO �ķ�����ȡÿһ�� Trade �е� TradeItem �ļ��ϣ�������װ��Ϊ Trade ������
		Set<Trade> trades = tradeDAO.getTradesWithUserId(userId);
		
		if(trades != null){
			Iterator<Trade> tradeIt = trades.iterator();
			
			while(tradeIt.hasNext()){
				Trade trade = tradeIt.next();
				
				int tradeId = trade.getTradeId();
				Set<TradeItem> items = tradeItemDAO.getTradeItemsWithTradeId(tradeId);
				
				if(items != null){
					for(TradeItem item: items){
						item.setBook(bookDAO.getBook(item.getBookId())); 
					}
					
					if(items != null && items.size() != 0){
						trade.setItems(items);						
					}
				}
				
				if(items == null || items.size() == 0){
					tradeIt.remove();	
				}
				
			}
		}
		
		if(trades != null && trades.size() != 0){
			user.setTrades(trades);			
		}
		
		return user;
	}
	*/
}
