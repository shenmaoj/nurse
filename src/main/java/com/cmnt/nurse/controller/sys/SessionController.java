package com.cmnt.nurse.controller.sys;

import com.cmnt.nurse.common.ZLRes;
import com.cmnt.nurse.common.utils.BeanCopyUtils;
import com.cmnt.nurse.common.utils.Constants;
import com.cmnt.nurse.model.sys.SysUser;
import com.cmnt.nurse.shiro.UserToken;
import com.cmnt.nurse.vo.sys.UserVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @auther shenmj
 * @create 2019/1/2
 **/
@Controller
@RequestMapping("")
public class SessionController {

    @RequestMapping(value = "/login.php", method = RequestMethod.POST)
    @ResponseBody
    public ZLRes<UserVO> logIn(HttpServletRequest request, @RequestBody Map<String, Object> map) {
        String userName = (String) map.get("userName");
        String password = (String) map.get("password");
        Subject subject = SecurityUtils.getSubject();
        UserToken userToken = new UserToken(userName, password);
        subject.login(userToken);
        SysUser bo = (SysUser) subject.getPrincipal();
        if (bo != null) {
            UserVO vo = new UserVO();
            BeanCopyUtils.copyProperties(bo, vo);
            subject.getSession().setAttribute(Constants.SESSION_CURRENT_USER, vo);
            return new ZLRes<UserVO>(vo);
        } else {
            subject.logout();
            return null;
        }
    }

    /**
     * 退出系统
     *
     * @param request
     * @return login.html
     */
    @RequestMapping(value = "/loginOut.php", method = RequestMethod.GET)
    public ModelAndView loginout(HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        try {
            HttpSession session = request.getSession();
            if (session != null) {
                session.invalidate();
            }
        } catch (Exception e) {
        }
        return new ModelAndView("redirect:/login.html");
    }
}
