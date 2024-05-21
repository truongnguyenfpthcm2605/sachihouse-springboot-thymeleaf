package com.shachi.shachihouse.utils;



import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CookieService {

    private final HttpServletResponse response;

    private final HttpServletRequest request;

    public void add(String name, String value, int days) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(days * 60 * 60);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public Cookie get(String name) {
        Cookie[] cookie = request.getCookies();
        if (cookie != null) {
            for (Cookie cookie2 : cookie) {
                if (cookie2.getName().equalsIgnoreCase(name)) ;
                return cookie2;
            }
        }
        return null;
    }

    public void remove(String name) {
        Cookie[] cookie = request.getCookies();
        if (cookie != null) {
            for (Cookie cookie2 : cookie) {
                if (cookie2.getName().equalsIgnoreCase(name)) ;
                cookie2.setMaxAge(0);
                cookie2.setPath("/");
                response.addCookie(cookie2);
            }
        }
    }

    public String getValue(String name) {
        Cookie[] cookie = request.getCookies();
        if (cookie != null) {
            for (Cookie cookie2 : cookie) {
                if (cookie2.getName().equalsIgnoreCase(name)) ;
                return cookie2.getValue();
            }
        }
        return null;
    }
}