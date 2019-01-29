package it.unitn.shoppinglesto.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter("/*")
public class CharacterEncodingFilter implements Filter {
    private FilterConfig filterConfig = null;

    @Override
    public void init(FilterConfig config) throws ServletException {
        // NOOP.
        this.filterConfig = config;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log("CharacterEncodingFilter:doFilter()");
        request.setCharacterEncoding("UTF-8");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // NOOP.
    }
    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

}