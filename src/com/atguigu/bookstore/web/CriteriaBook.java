package com.atguigu.bookstore.web;

/**
 *2019年1月6日下午11:12:01
 *
 */
public class CriteriaBook {

	private float minPrice=0;
	private float maxPrice = Integer.MAX_VALUE;
	private int pageNo;
	
	/**
	 * 
	 */
	public CriteriaBook() {
		super();
	}
	
	/**
	 * @param minPrice
	 * @param maxPrice
	 * @param pageNo
	 */
	public CriteriaBook(float minPrice, float maxPrice, int pageNo) {
		super();
		this.minPrice = minPrice;
		this.maxPrice = maxPrice;
		this.pageNo = pageNo;
	}

	/**
	 * @return the minPrice
	 */
	public float getMinPrice() {
		return minPrice;
	}
	/**
	 * @param minPrice the minPrice to set
	 */
	public void setMinPrice(float minPrice) {
		this.minPrice = minPrice;
	}
	/**
	 * @return the maxPrice
	 */
	public float getMaxPrice() {
		return maxPrice;
	}
	/**
	 * @param maxPrice the maxPrice to set
	 */
	public void setMaxPrice(float maxPrice) {
		this.maxPrice = maxPrice;
	}
	/**
	 * @return the pageNo
	 */
	public int getPageNo() {
		return pageNo;
	}
	/**
	 * @param pageNo the pageNo to set
	 */
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	
}
