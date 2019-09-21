package com.atguigu.bookstore.web;

import java.util.List;

/**
 * 2019年1月6日下午11:12:15
 *
 */
public class Page<T> {
	// 当前第几页
	private int pageNo;
	// 当前页的list
    private List<T> list;
    //每页显示多少条记录
    private int pageSize = 3;
    //总共多少条记录
    private long totalItemNumber;
    //构造器要对pageNo进行初始化
	public Page(int pageNo) {
		super();
		this.pageNo = pageNo;
	}
	
	//需要校验
	public int getPageNo() {
		if(pageNo < 0){
			pageNo = 1;
		}
		if(pageNo>getTotalPageNumber()){
			pageNo = getTotalPageNumber();
		}
		return pageNo;
	}
	
	
	public int getPageSize() {
		return pageSize;
	}
	
	public void setList(List<T> list) {
		this.list = list;
	}
	public List<T> getList() {
		return list;
	}
	//获取总页数
	private int getTotalPageNumber(){
		int totalPageNumber = (int)totalItemNumber / pageSize;
		if(totalItemNumber % pageSize != 0){
			totalPageNumber++;
		}
		return totalPageNumber;
	}
	public void setTotalItemNumber(long totalItemNumber) {
		this.totalItemNumber = totalItemNumber;
	}
	//判断是否有下一页
	public boolean isHasNext(){
		if(getPageNo() < getTotalPageNumber()){
			return true;
		}
		return false;
	}
	//判断是否有上一页
	public boolean isHasPrev(){
		if(getPageNo()>1){
			return true;
		}
		return false;
	}
	//获取上一页
	public int getNextPage(){
		/*if(getPageNo() == 1 ){
			return getPageNo();
		}*/
		if(isHasNext()){
			return getPageNo()+1;
		}
		return getPageNo();
	}
	//获取下一页
	public int getPrevPage(){
		if(isHasPrev()){
			return getPageNo()-1;
		}
		return getPageNo();
	}
    
}
