package com.cmnt.nurse.mybatis.page;

import java.util.ArrayList;

/**
 * 
 * @(#) PageResultList.java
 * @版权： 四川中疗网络科技有限公司
 * @描述： 分页结果集
 * @author leic
 * @version sma_review_V1.0
 * @createDate 2017年7月11日
 * @see
 */
public class PageResultList<E> extends ArrayList<E> {
	
	private static final long serialVersionUID = -3494364287880494068L;
	
	/** 页码，默认是第一页 */
	protected Integer pageNo = 1;
	
	/** 每页显示的记录数，默认是10 */
	protected Integer pageSize = 10;
	
	/** 总记录数 */
	protected Integer totalRecord;
	
	/** 总页数 */
	protected Integer totalPage;
	

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(Integer totalRecord) {
		this.totalRecord = totalRecord;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
}