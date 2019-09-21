package com.atguigu.bookstore.domain;

/**
 *2019年1月7日上午11:27:49
 *
 */
public class TradeItem {

	private Integer tradeItemId;
	private Book book;
	private int quantity;
	private Integer bookId;
	private Integer tradeId;
	
	public TradeItem() {
		super();
	}

	/**
	 * @param tradeItemId
	 * @param book
	 * @param quantity
	 * @param bookId
	 * @param tradeId
	 */
	public TradeItem(Integer tradeItemId, Book book, int quantity, Integer bookId, Integer tradeId) {
		super();
		this.tradeItemId = tradeItemId;
		this.book = book;
		this.quantity = quantity;
		this.bookId = bookId;
		this.tradeId = tradeId;
	}

	/**
	 * @return the tradeItemId
	 */
	public Integer getTradeItemId() {
		return tradeItemId;
	}

	/**
	 * @param tradeItemId the tradeItemId to set
	 */
	public void setTradeItemId(Integer tradeItemId) {
		this.tradeItemId = tradeItemId;
	}

	/**
	 * @return the book
	 */
	public Book getBook() {
		return book;
	}

	/**
	 * @param book the book to set
	 */
	public void setBook(Book book) {
		this.book = book;
	}

	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the bookId
	 */
	public Integer getBookId() {
		return bookId;
	}

	/**
	 * @param bookId the bookId to set
	 */
	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	/**
	 * @return the tradeId
	 */
	public Integer getTradeId() {
		return tradeId;
	}

	/**
	 * @param tradeId the tradeId to set
	 */
	public void setTradeId(Integer tradeId) {
		this.tradeId = tradeId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TradeItem [tradeItemId=" + tradeItemId + ", book=" + book + ", quantity=" + quantity + ", bookId="
				+ bookId + ", tradeId=" + tradeId + "]";
	}
	
	
}
