package com.example.kubsu.config.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;

@WebServlet(name = "GetCookietsServlet", value = "/GetCookietsServlet")
@Slf4j
public class GetCookietsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            log.info("receive cookie: " + cookie.getName() + "=" + cookie.getValue());
        }

        super.doGet(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        super.doGet(request, response);
    }
}
