package edu.fpt.yellowmoon.servlet;

import edu.fpt.yellowmoon.constant.PathConstants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/actions/*")
public class MainController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String uri = request.getRequestURI();

        String action = uri.substring(uri.lastIndexOf('/') + 1);

        String destination = null;

        switch(action) {
            case "register":
                destination = PathConstants.REGISTER_SERVLET;
                break;
        }

        if(destination == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Action not found");
        } else {
            request.getRequestDispatcher(destination).forward(request, response);
        }
    }
}