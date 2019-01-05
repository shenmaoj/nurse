package com.cmnt.nurse.common;

import com.cmnt.nurse.common.utils.Constants;
import com.cmnt.nurse.model.sys.SysUser;
import com.cmnt.nurse.vo.sys.UserVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;

/**
 * 
 * @(#) Context.java
 * @版权： 四川中疗网络科技有限公司
 * @描述： 获取session、user
 * @author leic
 * @version sma_review_V1.0
 * @createDate 2017年7月6日
 * @see
 */
@Component
public class Context {
 
	/**
	 * 获取当前session
	 * @return
	 */
	public static Session getSession() {
		Subject subject = SecurityUtils.getSubject();
		if (subject == null) {
			return null;
		}
		return subject.getSession();
	}

	public static UserVO getUserVO(){
		Session session = getSession();
		if(null != session){
			return (UserVO)session.getAttribute(Constants.SESSION_CURRENT_USER);
		}
		return null;
	}
	
	/**
	 * 获取当前用户
	 * @return UserBO
	 */
	public static SysUser getLoginUser() {
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject == null) {
				return null;
			}
			if (subject.getPrincipal() == null) {
				return null;
			}
			return (SysUser) subject.getPrincipal();
		} catch (Exception e) {
			return null;
		}
	}
	
	public static String getLoginUserId() {
		SysUser user = getLoginUser();
		if(null != user){
			return user.getId();
		}
		return null;
	}
	
}