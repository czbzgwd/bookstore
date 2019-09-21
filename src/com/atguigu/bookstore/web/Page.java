package com.atguigu.bookstore.web;

import java.util.List;

public class Page<T> {

	// 当前第几页
	private int pageNo;

	// 当前页的list(本页面需要显示的)
	private List<T> list;

	// 每页显示多少条记录
	private int pageSize = 3;

	// 共有多少条记录
	private long totalItemNumber;

	private int PrePage;
	private int NexPage;

	public int getPrePage() {
		return PrePage;
	}

	public int getNexPage() {
		return NexPage;
	}

	// 构造器中需要对 pageNo 进行初始化
	public Page(int pageNo) {
		super();
		this.pageNo = pageNo;
	}

	//返回当前页的页码, 需通过 getTotalPageNumber() 进行校验
	public int getPageNo() {
		if (pageNo < 0) {
			pageNo = 1;
		}
		if (pageNo > getTotalPageNumber()) {
			pageNo = getTotalPageNumber();
		}
		return pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public long getTotalItemNumber() {
		return totalItemNumber;
	}

	public void setTotalItemNumber(long totalItemNumber) {
		this.totalItemNumber = totalItemNumber;
	}

	// 获取总页码数, 需通过 totalItemNumber 和 pageSize 计算后返回
	public int getTotalPageNumber() {
		int totalPageNumber = (int) totalItemNumber / pageSize;
		if (totalItemNumber % pageSize != 0) {
			totalPageNumber++;
		}
		return totalPageNumber;
	}

	//判断是否有下一页
	public boolean isHasNext() {
		if (getPageNo() < getTotalPageNumber()) {
			return true;
		}
		return false;
	}

	//判断是否有上一页
	public boolean isHasPrev() {
		if (getPageNo() > 1) {
			return true;
		}
		return false;
	}

	//获取上一页
	public int getPrevPage() {
		if (isHasPrev()) {
			return getPageNo() - 1;
		}
		return getPageNo();

	}

	//获取下一页
	public int getNextPage() {
		if (isHasNext()) {
			return getPageNo() + 1;
		}
		return getPageNo();
	}
}
