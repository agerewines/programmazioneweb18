package it.unitn.shoppinglesto.filters;

import it.unitn.shoppinglesto.db.entities.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Making sure that the user is logged in
 * @author alessandrogerevini
 */
@WebFilter(filterName = "AuthenticationFilter")
public class AuthenticationFilter implements Filter {

    private static final boolean debug = true;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured.
    private FilterConfig filterConfig = null;

    public AuthenticationFilter() {
    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("AuthenticationFilter:DoBeforeProcessing");
        }

        if(request instanceof HttpServletRequest && response instanceof HttpServletResponse){
            HttpServletRequest req = (HttpServletRequest) request;

            HttpServletResponse resp = (HttpServletResponse) response;
            HttpSession session = req.getSession(false);
            User user = (User) session.getAttribute("user");
            if(user == null){
                if(!resp.isCommitted()){
                    String message = "You must be logged in to view this section";
                    session = req.getSession();
                    session.setAttribute("errorMessage", message);
                    session.setAttribute("action", "login");
                    session.setAttribute("returnUrl", req.getRequestURL());
                    resp.sendRedirect(resp.encodeRedirectURL(req.getContextPath() + "/home"));
                    return;
                }

            }
        }
    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("AuthenticationFilter:DoAfterProcessing");
        }
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
                log("AuthenticationFilter:Initializing filter");
            }
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("AuthenticationFilter()");
        }
        StringBuffer sb = new StringBuffer("AuthenticationFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }


    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }
}
