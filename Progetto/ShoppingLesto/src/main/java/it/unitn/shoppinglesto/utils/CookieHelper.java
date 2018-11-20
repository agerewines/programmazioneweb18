package it.unitn.shoppinglesto.utils;

import it.unitn.shoppinglesto.db.entities.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieHelper {
    private static final String IDENTIFIER = "shoppingLesto_authentication";

    /**
     * Creates a cookie containing the user's salt and adds it to the HttpServletResponse object
     * @param response
     * @param user
     */
    public static void storeUserCookie(HttpServletResponse response, User user){
        Cookie cookieId = new Cookie(IDENTIFIER, user.getUuid());
        cookieId.setMaxAge(24*60*60);
        cookieId.setPath("/");
        cookieId.setHttpOnly(true);
        response.addCookie(cookieId);
    }
    public static void storeGenericCookie(HttpServletResponse response,String id, String cookieValue){
        Cookie cookieId = new Cookie(id, cookieValue);
        cookieId.setMaxAge(24*60*60);
        cookieId.setPath("/");
        cookieId.setHttpOnly(true);
        response.addCookie(cookieId);
    }

    public static void removeGenericCookie(HttpServletResponse response,String id){
        Cookie cookieId = new Cookie(id, null);
        cookieId.setMaxAge(0);
        cookieId.setPath("/");
        response.addCookie(cookieId);
    }

    /**
     * Checks for the cookie containing the user's unique salt
     * @param request
     * @return the user's salt contained in the cookie
     */
    public static String getIdInCookie(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(IDENTIFIER.equals(cookie.getName()))
                    return cookie.getValue();
            }

        }
        return null;
    }

    public static void deleteUserCookie(HttpServletResponse response) {
        Cookie cookieId = new Cookie(IDENTIFIER, null);
        cookieId.setMaxAge(0);
        cookieId.setPath("/");
        response.addCookie(cookieId);
    }

    public static String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookieName.equals(cookie.getName()))
                    return cookie.getValue();
            }

        }
        return null;
    }
}
