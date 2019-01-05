package com.cmnt.nurse.common.datatables;

import java.util.Map;

/**
 * 
 * @(#) PageDataTablesRequest.java 
 * @版权： 四川中疗网络科技有限公司 
 * @描述： DataTables插件请求处理
 * @author leic
 * @version sma_review_V1.0
 * @createDate 2017年7月11日
 * @see
 */
public class DataTablesRequest {
	
	/** 当前页 */
	private Integer currentPage;
	
	/** 每页显示条数 */
	private Integer pageSize;
	
	public DataTablesRequest(Map<String,Object> map) {
		String page = map.get("page")==null?"1":map.get("page").toString();
		String length = map.get("length")==null?"10":map.get("length").toString();
		Integer _page, _length;
		try {
			_page = Integer.parseInt(page);
			_length = Integer.parseInt(length);
		} catch (Exception e) {
			_page = null;
			_length = null;
		}
		if (_page != null && _length != null) {
			currentPage = _page != 0 ? _page : 1;
			pageSize = _length;
		} else {
			currentPage = 1;
			pageSize = 10;
		}
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

}