package vn.trananhtuan.video.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import vn.trananhtuan.video.model.User_24133073;
import vn.trananhtuan.video.service.EmailServiceImpl_24133073;
import vn.trananhtuan.video.service.IEmailService_24133073;
import vn.trananhtuan.video.service.IUserService_24133073;
import vn.trananhtuan.video.service.UserServiceImpl_24133073;

@WebServlet(urlPatterns = "/register")
public class RegisterController_24133073
        extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final long OTP_VALID_TIME =
            5 * 60 * 1000L;

    private IUserService_24133073 userService;
    private IEmailService_24133073 emailService;

    @Override
    public void init() throws ServletException {

        userService =
                new UserServiceImpl_24133073();

        emailService =
                new EmailServiceImpl_24133073();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher(
                "/WEB-INF/views/auth/register.jsp"
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
                normalize(request.getParameter("username"));

        String password =
                request.getParameter("password");

        String confirmPassword =
                request.getParameter("confirmPassword");

        String fullname =
                normalize(request.getParameter("fullname"));

        String email =
                normalize(request.getParameter("email"));

        String phone =
                normalize(request.getParameter("phone"));

        saveFormData(
                request,
                username,
                fullname,
                email,
                phone
        );

        String validationError =
                validateInput(
                        username,
                        password,
                        confirmPassword,
                        fullname,
                        email
                );

        if (validationError != null) {

            forwardWithError(
                    request,
                    response,
                    validationError
            );

            return;
        }

        if (userService.existsByUsername(username)) {

            forwardWithError(
                    request,
                    response,
                    "Tên đăng nhập đã tồn tại."
            );

            return;
        }

        if (userService.existsByEmail(email)) {

            forwardWithError(
                    request,
                    response,
                    "Email đã được sử dụng."
            );

            return;
        }

        User_24133073 pendingUser =
                new User_24133073();

        pendingUser.setUsername(username);
        pendingUser.setPassword(password);
        pendingUser.setPhone(phone);
        pendingUser.setFullname(fullname);
        pendingUser.setEmail(email);
        pendingUser.setAdmin(false);
        pendingUser.setActive(true);
        pendingUser.setImages(null);

        String otpCode =
                emailService.generateOTP();

        boolean sent =
                emailService.sendOTP(
                        email,
                        otpCode
                );

        if (!sent) {

            forwardWithError(
                    request,
                    response,
                    "Không thể gửi OTP. "
                            + "Vui lòng kiểm tra cấu hình Gmail."
            );

            return;
        }

        HttpSession session =
                request.getSession(true);

        session.setAttribute(
                "pendingUser",
                pendingUser
        );

        session.setAttribute(
                "registrationOTP",
                otpCode
        );

        session.setAttribute(
                "otpExpiryTime",
                System.currentTimeMillis()
                        + OTP_VALID_TIME
        );

        response.sendRedirect(
                request.getContextPath()
                        + "/verify-otp"
        );
    }

    private String validateInput(
            String username,
            String password,
            String confirmPassword,
            String fullname,
            String email) {

        if (username == null
                || username.isBlank()
                || password == null
                || password.isBlank()
                || fullname == null
                || fullname.isBlank()
                || email == null
                || email.isBlank()) {

            return "Vui lòng nhập đầy đủ thông tin.";
        }

        if (username.length() > 50) {
            return "Tên đăng nhập tối đa 50 ký tự.";
        }

        if (password.length() < 6
                || password.length() > 100) {

            return "Mật khẩu phải từ 6 đến 100 ký tự.";
        }

        if (!password.equals(confirmPassword)) {
            return "Mật khẩu xác nhận không khớp.";
        }

        if (!email.matches(
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {

            return "Địa chỉ email không hợp lệ.";
        }

        return null;
    }

    private void saveFormData(
            HttpServletRequest request,
            String username,
            String fullname,
            String email,
            String phone) {

        request.setAttribute("username", username);
        request.setAttribute("fullname", fullname);
        request.setAttribute("email", email);
        request.setAttribute("phone", phone);
    }

    private void forwardWithError(
            HttpServletRequest request,
            HttpServletResponse response,
            String error)
            throws ServletException, IOException {

        request.setAttribute("error", error);

        request.getRequestDispatcher(
                "/WEB-INF/views/auth/register.jsp"
        ).forward(request, response);
    }

    private String normalize(String value) {

        if (value == null) {
            return null;
        }

        return value.trim();
    }
}