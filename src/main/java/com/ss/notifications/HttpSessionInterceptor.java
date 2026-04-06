package com.ss.notifications;

import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class HttpSessionInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request,
                                   org.springframework.http.server.ServerHttpResponse response,
                                   WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) {

    	try {
    	    if (request instanceof ServletServerHttpRequest servletRequest) {

    	        HttpServletRequest req = servletRequest.getServletRequest();
    	        HttpSession session = req.getSession(false);

    	        if (session != null) {
    	            Object user = session.getAttribute("loggedUser");

    	            System.out.println("SESSION USER: " + user);

    	            if (user != null) {
    	                attributes.put("loggedUser", user);
    	            }
    	        }
    	    }
    	} catch (Exception e) {
    	    e.printStackTrace(); // 🔥 THIS WILL SHOW REAL ERROR
    	}

        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request,
                               org.springframework.http.server.ServerHttpResponse response,
                               WebSocketHandler wsHandler,
                               Exception exception) {
    }
}