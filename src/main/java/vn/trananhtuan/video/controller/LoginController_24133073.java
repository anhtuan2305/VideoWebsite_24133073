package vn.trananhtuan.video.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import vn.trananhtuan.video.model.User_24133073;
import vn.trananhtuan.video.service.IUserService_24133073;
import vn.trananhtuan.video.service.UserServiceImpl_24133073;

@WebServlet(urlPatterns = "/login")
public class LoginController_24133073
        extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private IUserService_24133073 userService;

    @Override
    public void init() throws ServletException {

        userService =
                new UserServiceImpl_24133073();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session =
                request.getSession(false);

        if (session != null) {

            User_24133073 currentUser =
                    (User_24133073) session.getAttribute(
                            "currentUser"
                    );

            if (currentUser != null) {

                redirectAfterLogin(
                        request,
                        response,
                        currentUser
                );

                return;
            }
        }

        request.getRequestDispatcher(
                "/WEB-INF/views/auth/login.jsp"
        ).forward(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String username =
                request.getParameter("username");

        String password =
                request.getParameter("password");

        User_24133073 user =
                userService.login(
                        username,
                        password
                );

        if (user == null) {

            request.setAttribute(
                    "error",
                    "Tên đăng nhập hoặc mật khẩu không đúng."
            );

            request.setAttribute(
                    "username",
                    username
            );

            request.getRequestDispatcher(
                    "/WEB-INF/views/auth/login.jsp"
            ).forward(request, response);

            return;
        }

        HttpSession oldSession =
                request.getSession(false);

        if (oldSession != null) {
            oldSession.invalidate();
        }

        HttpSession newSession =
                request.getSession(true);

        newSession.setAttribute(
                "currentUser",
                user
        );

        newSession.setMaxInactiveInterval(
                30 * 60
        );

        redirectAfterLogin(
                request,
                response,
                user
        );
    }

    private void redirectAfterLogin(
            HttpServletRequest request,
            HttpServletResponse response,
            User_24133073 user)
            throws IOException {

        String contextPath =
                request.getContextPath();

        if (user.isAdmin()) {

            response.sendRedirect(
                    contextPath + "/admin/users"
            );

        } else {

            response.sendRedirect(
                    contextPath + "/home"
            );
        }
    }
}