package com.cmnt.nurse.common.datatables;

import javax.servlet.http.HttpServletRequest;
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

	/** Datatables发送的draw是多少那么服务器就返回多少 */
	private Integer draw;
	
	public DataTablesRequest(HttpServletRequest request) {
		String _draw = request.getParameter("draw");
		String start = request.getParameter("start");
		String length = request.getParameter("length");
		String iDisplayStart = request.getParameter("iDisplayStart");
		String iDisplayLength = request.getParameter("iDisplayLength");
		Integer _start = null, _length = null, _iDisplayStart = null, _iDisplayLength = null;
		try {
			draw = _draw == null ? 0 : Integer.parseInt(_draw);
			_start = Integer.parseInt(start);
			_length = Integer.parseInt(length);
		} catch (Exception e) {
			_draw = null;
			_start = null;
			_length = null;
		}
		try {
			_iDisplayStart = Integer.parseInt(iDisplayStart);
			_iDisplayLength = Integer.parseInt(iDisplayLength);
		} catch (Exception e) {
			_iDisplayStart = null;
			_iDisplayLength = null;
		}
		if (_start != null && _length != null) {
			currentPage = _start != 0 ? (_start / _length + 1) : 1;
			pageSize = _length;
		} else if (_iDisplayStart != null && _iDisplayLength != null) {
			currentPage = _iDisplayStart != 0 ? (_iDisplayStart / _iDisplayLength + 1) : 1;
			pageSize = _iDisplayLength;
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

	public Integer getDraw() {
		return draw;
	}
}