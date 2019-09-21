package com.atguigu.bookstore.web;

import java.util.List;

public class Page<T> {

	// ��ǰ�ڼ�ҳ
	private int pageNo;

	// ��ǰҳ��list(��ҳ����Ҫ��ʾ��)
	private List<T> list;

	// ÿҳ��ʾ��������¼
	private int pageSize = 3;

	// ���ж�������¼
	private long totalItemNumber;

	private int PrePage;
	private int NexPage;

	public int getPrePage() {
		return PrePage;
	}

	public int getNexPage() {
		return NexPage;
	}

	// ����������Ҫ�� pageNo ���г�ʼ��
	public Page(int pageNo) {
		super();
		this.pageNo = pageNo;
	}

	//���ص�ǰҳ��ҳ��, ��ͨ�� getTotalPageNumber() ����У��
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

	// ��ȡ��ҳ����, ��ͨ�� totalItemNumber �� pageSize ����󷵻�
	public int getTotalPageNumber() {
		int totalPageNumber = (int) totalItemNumber / pageSize;
		if (totalItemNumber % pageSize != 0) {
			totalPageNumber++;
		}
		return totalPageNumber;
	}

	//�ж��Ƿ�����һҳ
	public boolean isHasNext() {
		if (getPageNo() < getTotalPageNumber()) {
			return true;
		}
		return false;
	}

	//�ж��Ƿ�����һҳ
	public boolean isHasPrev() {
		if (getPageNo() > 1) {
			return true;
		}
		return false;
	}

	//��ȡ��һҳ
	public int getPrevPage() {
		if (isHasPrev()) {
			return getPageNo() - 1;
		}
		return getPageNo();

	}

	//��ȡ��һҳ
	public int getNextPage() {
		if (isHasNext()) {
			return getPageNo() + 1;
		}
		return getPageNo();
	}
}
