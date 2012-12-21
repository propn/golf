package com.golf.mvc;

import java.io.IOException;
import java.util.concurrent.FutureTask;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.golf.Golf;
import com.golf.mvc.anno.MediaType;
import com.golf.mvc.view.Builder;
import com.golf.mvc.view.BuilderFactory;
import com.golf.mvc.view.ErrorViewBuilder;
import com.golf.rbac.SecurityMgr;
import com.golf.utils.StringUtils;

/**
 * 
 * 
 * @author Thunder.Hsu
 * @CreateDate 2012-11-17
 */
public class GolfFilter extends Golf implements Filter {

    private static final Logger log = LoggerFactory.getLogger(GolfFilter.class);
    private static final String IGNORE_FILE = "^(.+[.])(png|gif|jpg|ttf|woff|eot|svg|js|css|jpeg|swf|html|jsp|jspx)$";
    private static Pattern ignoreFilePattern = null;
    private static Pattern ignorePathPattern = null;// console.\\S{0,}

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        long begin = System.currentTimeMillis();

        log.info("golf begin start.");
        appPath = filterConfig.getServletContext().getRealPath("/");
        log.info("Path [{}] ", appPath);
        //
        String regex = filterConfig.getInitParameter("ignore_file");
        if (null == regex) {
            ignoreFilePattern = Pattern.compile(IGNORE_FILE, Pattern.CASE_INSENSITIVE);
            log.debug("ignorePattern : " + IGNORE_FILE);
        } else {
            ignoreFilePattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        }
        // ignore_path
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
        //
        String packages = filterConfig.getInitParameter("packages");
        String[] pkgs = null;
        if (null == packages) {
            pkgs = getPkgs();
            log.debug("Packages : [{}]", StringUtils.array2Strig(pkgs, ';'));
        } else {
            pkgs = packages.split(";");
            log.debug("Packages : " + packages);
        }
        try {
            init(pkgs);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
        log.info("golf started. Time used(millis): " + String.valueOf(System.currentTimeMillis() - begin));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse rsp = (HttpServletResponse) response;
            doFilter(req, rsp, chain);
        } catch (ClassCastException e) {
            throw new ServletException("non-HTTP request or response");
        }
    }

    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // CharacterEncodingFilter
        request.setCharacterEncoding(Golf.charsetName);
        response.setCharacterEncoding(Golf.charsetName);
        // 更新用户最后访问时间
        String sessionId = request.getSession().getId();
        SecurityMgr.get(sessionId);
        //
        String servletPath = request.getServletPath();
        if (null != ignorePathPattern) {
            String path = servletPath;
            if (!path.endsWith("/")) {
                path = servletPath + "/";
            }
            if (ignorePathPattern.matcher(path).find()) {
                chain.doFilter(request, response);
                return;
            }
        }
        //
        if (ignoreFilePattern.matcher(servletPath).matches()) {
            // 静态文件设置缓存
            response.setDateHeader("Expires", System.currentTimeMillis() + 86400000);// 1000*60*60*24 一天
            chain.doFilter(request, response);
            return;
        }
        // 禁止客户端缓存
        response.setDateHeader("Expires", -10);
        response.setHeader("Cache-Control", "no-store,no-cache,must-revalidate");
        response.setHeader("Pragma", "no-cache");
        //
        Resource res = ResUtils.getMatchedRes(servletPath);
        if (!validate(request, response, res)) {
            // HTTP 404 Not Found
            // 406 Not Acceptable Content-Type
            // 405 Method Not Allowed
            // 415 Unsupported Media Type
            return;
        }
        
        String accept = request.getHeader("Accept");
        String[] produces = res.getProduces();
        String mediaType = getOptimalType(accept, produces);
        if (null == mediaType) {
            // 406 Not Acceptable Content-Type
            response.setStatus(406);
            response.setContentType("text/plain");
            response.getWriter().append("Not Acceptable Content-Type").flush();
            return;
        }
        call(request, response, res, mediaType);
    }

    private void call(HttpServletRequest request, HttpServletResponse response, Resource res, String mediaType)
            throws IOException, ServletException {
        Atom atom = new Atom(request, response, res);
        FutureTask<Object> transMgr = new FutureTask<Object>(atom);
        new Thread(transMgr).start();
        Object rst = null;
        try {
            rst = transMgr.get();
        } catch (Exception e) {
            // FutureTask
            throw new RuntimeException(e);
        }
        Builder builder = BuilderFactory.getBuilder(mediaType, rst);
        if (null == builder) {
            builder = new ErrorViewBuilder();
            builder.build(request, response, mediaType, new ServletException("系统不支持的视图" + mediaType + " "
                    + rst.getClass().getName()));
            return;
        }
        builder.build(request, response, mediaType, rst);
    }

    private boolean validate(final HttpServletRequest request, HttpServletResponse response, Resource res)
            throws IOException {

        if (null == res) {
            // HTTP 404 Not Found
            response.setStatus(404);
            response.setContentType("text/plain");
            response.getWriter().append("Not Found").flush();
            return false;
        }
        String encoding = request.getCharacterEncoding();
        if (null != encoding && !encoding.equals(Golf.charsetName)) {
            // 405 Method Not Allowed
            response.setStatus(400);
            response.setContentType(MediaType.TEXT_PLAIN);
            response.setHeader("Allow", Golf.charsetName);
            response.getWriter().append("CharacterEncoding Not Allowed!").flush();// Allow
            response.getWriter().close();
            return false;
        }

        String acceptHttpMethods = res.getHttpMethod();
        if (!acceptHttpMethods.contains(request.getMethod())) {
            // 405 Method Not Allowed
            response.setStatus(405);
            response.setContentType(MediaType.TEXT_PLAIN);
            response.setHeader("Allow", acceptHttpMethods);
            response.getWriter().append("Method Not Allowed!").flush();// Allow
            response.getWriter().close();
            return false;
        }

        String consumes = res.getConsumes();
        String contentType = request.getContentType();
        if (null != contentType && !consumes.isEmpty()) {
            contentType = contentType.split(";")[0];
            if (!consumes.contains(contentType)) {
                // 415 Unsupported Media Type
                response.setStatus(415);
                response.setContentType(MediaType.TEXT_PLAIN);
                response.setHeader("Support", consumes);
                response.getWriter().append("Unsupported Media Type!").flush();// Support
                return false;
            }
        }
        return true;
    }

    private String getOptimalType(String accept, String[] produces) {
        if (null == produces || produces.length == 0) {
            return MediaType.APPLICATION_JSON;
        }
        if (null == accept || accept.contains("*/*")) {
            return produces[0];
        }
        for (String type : produces) {
            if (accept.contains(type)) {
                return type;
            }
        }
        return null;
    }

    @Override
    public void destroy() {

    }

}
