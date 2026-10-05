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

@WebServlet(urlPatterns = "/verify-otp")
public class VerifyOTPController_24133073
        extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final long OTP_VALID_TIME =
            5 * 60 * 1000L;

    private static final int MAX_ATTEMPTS = 5;

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

        HttpSession session =
                request.getSession(false);

        if (session == null
                || session.getAttribute(
                        "pendingUser") == null) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/register"
            );

            return;
        }

        User_24133073 pendingUser =
                (User_24133073) session.getAttribute(
                        "pendingUser"
                );

        request.setAttribute(
                "email",
                maskEmail(pendingUser.getEmail())
        );

        request.getRequestDispatcher(
                "/WEB-INF/views/auth/verify-otp.jsp"
        ).forward(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session =
                request.getSession(false);

        if (session == null) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/register"
            );

            return;
        }

        User_24133073 pendingUser =
                (User_24133073) session.getAttribute(
                        "pendingUser"
                );

        if (pendingUser == null) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/register"
            );

            return;
        }

        String action =
                request.getParameter("action");

        if ("resend".equals(action)) {

            resendOTP(
                    request,
                    response,
                    session,
                    pendingUser
            );

            return;
        }

        verifyOTP(
                request,
                response,
                session,
                pendingUser
        );
    }

    private void verifyOTP(
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session,
            User_24133073 pendingUser)
            throws ServletException, IOException {

        String enteredOTP =
                request.getParameter("otp");

        String savedOTP =
                (String) session.getAttribute(
                        "registrationOTP"
                );

        Long expiryTime =
                (Long) session.getAttribute(
                        "otpExpiryTime"
                );

        Integer attempts =
                (Integer) session.getAttribute(
                        "otpAttempts"
                );

        if (attempts == null) {
            attempts = 0;
        }

        if (expiryTime == null
                || System.currentTimeMillis()
                > expiryTime) {

            session.removeAttribute(
                    "registrationOTP"
            );

            forwardWithError(
                    request,
                    response,
                    pendingUser,
                    "Mã OTP đã hết hạn. "
                            + "Vui lòng gửi lại OTP."
            );

            return;
        }

        if (attempts >= MAX_ATTEMPTS) {

            forwardWithError(
                    request,
                    response,
                    pendingUser,
                    "Bạn đã nhập sai quá 5 lần. "
                            + "Vui lòng gửi lại OTP."
            );

            return;
        }

        if (enteredOTP == null
                || !enteredOTP.trim().equals(savedOTP)) {

            attempts++;

            session.setAttribute(
                    "otpAttempts",
                    attempts
            );

            forwardWithError(
                    request,
                    response,
                    pendingUser,
                    "Mã OTP không chính xác. "
                            + "Bạn còn "
                            + (MAX_ATTEMPTS - attempts)
                            + " lần thử."
            );

            return;
        }

        boolean registered =
                userService.register(pendingUser);

        if (!registered) {

            forwardWithError(
                    request,
                    response,
                    pendingUser,
                    "Đăng ký thất bại. "
                            + "Tài khoản hoặc email có thể đã tồn tại."
            );

            return;
        }

        clearRegistrationSession(session);

        response.sendRedirect(
                request.getContextPath()
                        + "/login?registered=true"
        );
    }

    private void resendOTP(
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session,
            User_24133073 pendingUser)
            throws ServletException, IOException {

        String newOTP =
                emailService.generateOTP();

        boolean sent =
                emailService.sendOTP(
                        pendingUser.getEmail(),
                        newOTP
                );

        if (!sent) {

            forwardWithError(
                    request,
                    response,
                    pendingUser,
                    "Không thể gửi lại OTP. "
                            + "Vui lòng kiểm tra cấu hình Gmail."
            );

            return;
        }

        session.setAttribute(
                "registrationOTP",
                newOTP
        );

        session.setAttribute(
                "otpExpiryTime",
                System.currentTimeMillis()
                        + OTP_VALID_TIME
        );

        session.setAttribute(
                "otpAttempts",
                0
        );

        request.setAttribute(
                "message",
                "Mã OTP mới đã được gửi đến email."
        );

        forwardToPage(
                request,
                response,
                pendingUser
        );
    }

    private void forwardWithError(
            HttpServletRequest request,
            HttpServletResponse response,
            User_24133073 pendingUser,
            String error)
            throws ServletException, IOException {

        request.setAttribute("error", error);

        forwardToPage(
                request,
                response,
                pendingUser
        );
    }

    private void forwardToPage(
            HttpServletRequest request,
            HttpServletResponse response,
            User_24133073 pendingUser)
            throws ServletException, IOException {

        request.setAttribute(
                "email",
                maskEmail(pendingUser.getEmail())
        );

        request.getRequestDispatcher(
                "/WEB-INF/views/auth/verify-otp.jsp"
        ).forward(request, response);
    }

    private void clearRegistrationSession(
            HttpSession session) {

        session.removeAttribute("pendingUser");
        session.removeAttribute("registrationOTP");
        session.removeAttribute("otpExpiryTime");
        session.removeAttribute("otpAttempts");
    }

    private String maskEmail(String email) {

        if (email == null || !email.contains("@")) {
            return email;
        }

        int atIndex = email.indexOf('@');

        String name = email.substring(0, atIndex);
        String domain = email.substring(atIndex);

        if (name.length() <= 2) {
            return name.charAt(0) + "***" + domain;
        }

        return name.substring(0, 2)
                + "***"
                + domain;
    }
}