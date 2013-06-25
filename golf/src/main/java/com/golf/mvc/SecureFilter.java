/**
 * 
 */
package com.golf.mvc;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.golf.mvc.session.ISession;
import com.golf.mvc.session.Session;
import com.golf.mvc.session.SessionManager;
import com.golf.utils.StringUtils;

/**
 * @author Thunder.Hsu 2013-6-25
 */
public class SecureFilter implements Filter {
    /* 忽略路径 */
    private static Pattern ignorePathPattern = null;// console.\\S{0,}

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // GolfMVC忽略路径
        String ignore_path = filterConfig.getInitParameter("ignore_path");
        if (null != ignore_path) {
            String paths[] = ignore_path.split(";");
            StringBuffer pattern = new StringBuffer();
            for (String path : paths) {
                if (StringUtils.isBlank(path)) {
                    continue;
                }
                pattern.append("^");
                if (!path.startsWith("/")) {
                    pattern.append("/");
                }
                pattern.append(path);
                if (!path.endsWith("/")) {
                    pattern.append("/");
                }
                pattern.append(".*|");
            }
            if (pattern.length() > 0) {
                ignorePathPattern = Pattern.compile(pattern.subSequence(0, pattern.length() - 1).toString(),
                        Pattern.CASE_INSENSITIVE);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse,
     * javax.servlet.FilterChain)
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse rsp = (HttpServletResponse) response;
        // 绑定Session
        String sId = req.getSession().getId();
        ISession session = SessionManager.getSession(sId);
        if (null == session) {
            session = new Session();
            session.setId(sId);
        }
        SessionManager.setSession(session);
        doFilter(req, rsp, chain);
    }

    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        //
        String sessionId = request.getSession().getId();
        ISession session = SessionManager.getSession(sessionId);
        //
        if (null == ignorePathPattern) { // 忽略路径
            check(request, response, session);
            chain.doFilter(request, response);
            return;
        } else {
            String servletPath = request.getServletPath();
            String path = servletPath;
            if (!path.endsWith("/")) {
                path = servletPath + "/";
            }
            if (ignorePathPattern.matcher(path).find()) {
                chain.doFilter(request, response);
                return;
            } else {
                // 登录信息校验
                check(request, response, session);
                chain.doFilter(request, response);
                return;
            }
        }
    }

    /**
     * 校验用户权限
     * 
     * @param session
     * @return
     */
    private boolean check(ServletRequest request, ServletResponse response, ISession session) {
        //
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.Filter#destroy()
     */
    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

}
