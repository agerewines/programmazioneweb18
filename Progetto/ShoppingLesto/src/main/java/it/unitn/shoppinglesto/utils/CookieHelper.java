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
        //cookieId.setSecure(true);
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
}
