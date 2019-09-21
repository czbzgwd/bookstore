package com.atguigu.bookstore.domain;

import java.sql.Date;

/**
 *2019年1月6日下午11:11:33
 *
 */
public class Book {



	private Integer id;
	private String author;
	private String title;
	private float price;
	private Date publishingDate;
	private int salesAmount;
	private int storeNumber;
	private String remark;
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}
	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the price
	 */
	public float getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(float price) {
		this.price = price;
	}
	/**
	 * @return the publishingDate
	 */
	public Date getPublishingDate() {
		return publishingDate;
	}
	/**
	 * @param publishingDate the publishingDate to set
	 */
	public void setPublishingDate(Date publishingDate) {
		this.publishingDate = publishingDate;
	}
	/**
	 * @return the salesAmount
	 */
	public int getSalesAmount() {
		return salesAmount;
	}
	/**
	 * @param salesAmount the salesAmount to set
	 */
	public void setSalesAmount(int salesAmount) {
		this.salesAmount = salesAmount;
	}
	/**
	 * @return the storeNumber
	 */
	public int getStoreNumber() {
		return storeNumber;
	}
	/**
	 * @param storeNumber the storeNumber to set
	 */
	public void setStoreNumber(int storeNumber) {
		this.storeNumber = storeNumber;
	}
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Book [id=" + id + ", author=" + author + ", title=" + title + ", price=" + price + ", publishingDate="
				+ publishingDate + ", salesAmount=" + salesAmount + ", storeNumber=" + storeNumber + ", remark="
				+ remark + "]";
	}
	
}
