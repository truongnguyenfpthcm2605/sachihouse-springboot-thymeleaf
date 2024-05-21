package com.shachi.shachihouse.utils;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class Session {
    private final HttpSession session;
    public void setAttribute(String name,Object value) {
        session.setAttribute(name, value);
    }

    public <T> T getAttribute(String name) {
        T s = (T) session.getAttribute(name);
        return s == null ? null : s;
    }
    public void removeAttribute(String name) {
        session.removeAttribute(name);
    }
}
