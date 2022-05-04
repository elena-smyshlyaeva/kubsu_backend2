package com.example.kubsu.config.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;

@WebServlet(name = "SetCookiesServlet", value = "/SetCookiesServlet")
@Slf4j
public class SetCookiesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie name = new Cookie("name", "test-name");
        name.setMaxAge(60*2);
        Cookie email = new Cookie("email", "test-email");
        email.setMaxAge(60*2);

        response.addCookie(name);
        response.addCookie(email);
        log.info("cookies has been added");

        super.doGet(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
    }
}
