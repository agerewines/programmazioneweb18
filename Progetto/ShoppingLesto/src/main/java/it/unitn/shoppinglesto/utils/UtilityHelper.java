package it.unitn.shoppinglesto.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class UtilityHelper {

    /**
     * Return the root url of the application
     * @param request
     * @return root url of the application
     */
    public static String getURLWithContextPath(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();

    }

    public static String getCookieValue(HttpServletRequest request, String templistcookiename) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(templistcookiename.equals(cookie.getName()))
                    return cookie.getValue();
            }

        }
        return null;
    }
}
