package com.cmnt.nurse.common.datatables;



import com.cmnt.nurse.mybatis.page.PageResultList;

import java.util.HashMap;

/**
 * 
 * @(#) DataTablesResponse.java
 * @版权： 四川中疗网络科技有限公司
 * @描述： DataTables返回值处理
 * @author leic
 * @version sma_review_V1.0
 * @createDate 2017年7月11日
 * @see
 */
public class DataTablesResponse extends HashMap<String, Object> {

	private static final long serialVersionUID = -2750736673833454302L;

	public DataTablesResponse(PageResultList<?> list) {
		put("recordsFiltered", list.getTotalRecord());
		put("recordsTotal", list.getTotalRecord());
		put("totalPage", list.getTotalPage());
		put("pageNo", list.getPageNo());
		put("data", list);
	}
	
}