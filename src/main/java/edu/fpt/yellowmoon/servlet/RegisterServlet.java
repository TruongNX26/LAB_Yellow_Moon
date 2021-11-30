package edu.fpt.yellowmoon.servlet;

import edu.fpt.yellowmoon.constant.DBConstants;
import edu.fpt.yellowmoon.constant.PathConstants;
import edu.fpt.yellowmoon.constant.ValidationConstants;
import edu.fpt.yellowmoon.dao.UserDAO;
import edu.fpt.yellowmoon.dto.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@WebServlet("/register-servlet")
public class RegisterServlet extends HttpServlet {

    public static final String USER_EMAIL = "txtEmail";
    public static final String USER_FULL_NAME = "txtFullName";
    public static final String USER_PASSWORD = "txtPassword";
    public static final String USER_CONFIRM_PASSWORD = "txtConfirmPassword";

    private static final String SUCCESS = PathConstants.LOGIN_PAGE;
    private static final String FAILED = PathConstants.REGISTER_PAGE;

    private UserDAO dao;

    @Override
    public void init() throws ServletException {
        super.init();
        dao = UserDAO.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter(USER_EMAIL);
        String fullName = request.getParameter(USER_FULL_NAME);
        String password = request.getParameter(USER_PASSWORD);
        String confirm = request.getParameter(USER_CONFIRM_PASSWORD);

        User user = User.builder()
                .email(email.trim())
                .password(password.trim())
                .fullName(fullName.trim())
                .roleId(DBConstants.ROLE_CUSTOMER)
                .build();

        HashMap<String, String> errors = validate(user);
        if(!user.getPassword().equals(confirm)) {
            errors.put(USER_CONFIRM_PASSWORD, "Confirm password does not match");
        }

        String destination = null;
        if(errors.isEmpty()) {
            if(dao.register(user)) {
                destination = SUCCESS;
                request.setAttribute("status", "created");
            }
            else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "System error!");
                return;
            }
        } else {
            request.setAttribute("ERRORS", errors);
            destination = FAILED;
        }

        request.getRequestDispatcher(PathConstants.STATIC_PREFIX + destination).forward(request, response);
    }

    private HashMap<String, String> validate(User user) {
        HashMap<String, String> errors = new HashMap<>();

        if(dao.checkExistEmail(user.getEmail())) {
            errors.put(USER_EMAIL, "Email already exist");
        } else if(!user.getEmail().matches(ValidationConstants.EMAIL)) {
            errors.put(USER_EMAIL, "Email length must follow example@email.xxx (RFC 5322)");
        }

        if(user.getFullName().length() < 6 || user.getFullName().length() > 30) {
            errors.put(USER_FULL_NAME, "User full name length 6 - 30, only A-Z and a-z");
        }

        if(!user.getPassword().matches(ValidationConstants.PASSWORD)) {
            errors.put(USER_PASSWORD, "Password contain character and digits only, length 6-30");
        }
        return errors;
    }
}