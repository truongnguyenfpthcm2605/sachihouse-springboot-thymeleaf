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
        return (T) session.getAttribute(name);
    }
    public void removeAttribute(String name) {
        session.removeAttribute(name);
    }
}
