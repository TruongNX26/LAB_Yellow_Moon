package edu.fpt.yellowmoon.servlet;

import edu.fpt.yellowmoon.constant.PathConstants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/pages/*")
public class PageController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        String page = uri.substring(uri.lastIndexOf('/') + 1);

        String destination = null;
        switch (page) {
            case "login":
                destination = PathConstants.LOGIN_PAGE;
                break;
            case "register":
                destination = PathConstants.REGISTER_PAGE;
                break;
        }

        if(destination == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Page not found");
        } else {
            request.getRequestDispatcher(PathConstants.STATIC_PREFIX + destination).forward(request, response);
        }
    }
}
