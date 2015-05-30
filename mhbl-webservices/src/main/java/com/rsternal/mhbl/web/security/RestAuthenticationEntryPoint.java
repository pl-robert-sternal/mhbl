package com.rsternal.mhbl.web.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class RestAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    private static final String UNAUTHORIZED_INFO = "unauthorized";

    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e)
            throws IOException, ServletException {
        httpServletResponse.addHeader("WWW-Authenticate", "Basic realm=\"" + getRealmName() + "\"");
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter writer = httpServletResponse.getWriter();
        writer.println("HTTP Status 401 - " + e.getMessage());
        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, UNAUTHORIZED_INFO);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        setRealmName("demo");
        super.afterPropertiesSet();
    }
}
