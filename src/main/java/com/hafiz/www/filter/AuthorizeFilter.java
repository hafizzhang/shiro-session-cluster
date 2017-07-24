package com.hafiz.www.filter;

import com.alibaba.fastjson.JSON;
import com.hafiz.www.consts.AppConst;
import com.hafiz.www.vo.JsonResult;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Writer;

/**
 * Desc:认证验证过滤器
 * Created by hafiz.zhang on 2017/7/21.
 */
public class AuthorizeFilter extends AuthorizationFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        Subject subject = getSubject(request, response);
        if (!subject.isAuthenticated()) {
            String requestURI = request.getRequestURI();
            if (requestURI.endsWith(".json")) {
                JsonResult jr = new JsonResult();
                jr.setCode(AppConst.UNAUTHORIZED);
                jr.setMsg("登陆超时,请重新登录");
                response.setStatus(AppConst.UNAUTHORIZED);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json;charset=UTF-8");
                Writer w = response.getWriter();
                w.write(JSON.toJSONString(jr));
                w.flush();
                w.close();
            } else {
                response.sendRedirect(request.getContextPath() + "/login.html");
            }
            return false;
        }
        Boolean isAjax = isAjax(request);
        if (subject.getPrincipal() != null && isAjax) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(AppConst.UNAUTHORIZED);
            Writer w = response.getWriter();
            JsonResult jr = new JsonResult();
            jr.setCode(AppConst.UNAUTHORIZED);
            jr.setMsg("无权限操作!");
            w.write(JSON.toJSONString(jr));
            w.flush();
            w.close();
            return  false;
        }
        return super.onAccessDenied(servletRequest, servletResponse);
    }

    /**
     * 根据请求头判断是不是ajax请求
     *
     * @param request 请求实体
     *
     * @return
     */
    private Boolean isAjax(HttpServletRequest request) {
        Boolean isAjax = request.getHeader("X-Requested-With") != null
                            && "XMLHttpRequest".equals( request.getHeader("X-Requested-With").toString());
        return isAjax;
    }

    /**
     * 判断用户是否可以访问请求的资源
     *
     * @param request 用户请求
     *
     * @param response 响应
     *
     * @param mappedValue
     *
     * @return
     *
     * @throws Exception
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response,
                                      Object mappedValue) throws Exception {
        // 登陆请求直接放行
        if (isLoginRequest(request, response)) {
            return true;
        }

        // 获取用户认证信息
        Subject subject = getSubject(request, response);
        if (!subject.isAuthenticated()) {
            HttpServletRequest httpServletRequest = (HttpServletRequest)request;
            HttpSession session = httpServletRequest.getSession();
            String requestUrl = httpServletRequest.getRequestURL().toString();
            session.setAttribute(AppConst.LAST_URL_KEY, requestUrl);
            return false;
        }

        // 判断请求资源是否授权
        /*String resource = getPathWithinApplication(request);
        if (subject.isPermitted(resource)) {
            return true;
        }*/
        return true;
    }
}
