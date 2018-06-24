package com.dsp.ad.filter;

import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@WebFilter(urlPatterns = "/*")
@Order(value = 1)
public class UserFilter implements Filter {

    private static final Set<String> ALLOWED_PATHS = Collections.unmodifiableSet(new HashSet<>(
            Arrays.asList(".*/css/.*", ".*/images/.*", ".*/fonts/.*", ".*/js/.*", ".*/login", ".*/logout","/mgr.*","/toUser.*",".*.png",".*.jpg")));

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = ((HttpServletRequest) req).getSession();
        String path = request.getRequestURI().substring(request.getContextPath().length()).replaceAll("[/]+$", "");
        boolean allowedPath = false;
        for (String regular : ALLOWED_PATHS) {
            if (path.matches(regular)) {
                allowedPath = true;
            }
        }

        if (allowedPath) {
            chain.doFilter(req, res);
        } else if (session.getAttribute("user") != null) {
            chain.doFilter(req, res);
        } else {
            response.sendRedirect(request.getContextPath() + "/user/login");
        }
    }

    @Override
    public void destroy() {

    }
}
