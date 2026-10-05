package vn.trananhtuan.video.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import vn.trananhtuan.video.model.User_24133073;
import vn.trananhtuan.video.service.IUserService_24133073;
import vn.trananhtuan.video.service.UserServiceImpl_24133073;

@WebServlet(
        urlPatterns = {
                "/admin/users",
                "/admin/users/*"
        }
)
public class AdminUserController_24133073
        extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final int PAGE_SIZE = 6;

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

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String path = request.getPathInfo();

        if (path == null || "/".equals(path)) {
            showUserList(request, response);
            return;
        }

        switch (path) {

            case "/create":
                showCreateForm(request, response);
                break;

            case "/edit":
                showEditForm(request, response);
                break;

            default:
                response.sendError(
                        HttpServletResponse.SC_NOT_FOUND
                );
                break;
        }
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String path = request.getPathInfo();

        if (path == null) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST
            );
            return;
        }

        switch (path) {

            case "/create":
                createUser(request, response);
                break;

            case "/edit":
                updateUser(request, response);
                break;

            case "/delete":
                deleteUser(request, response);
                break;

            default:
                response.sendError(
                        HttpServletResponse.SC_NOT_FOUND
                );
                break;
        }
    }

    private void showUserList(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        int page = parsePage(
                request.getParameter("page")
        );

        int totalUsers = userService.count();

        int totalPages = (int) Math.ceil(
                (double) totalUsers / PAGE_SIZE
        );

        if (totalPages < 1) {
            totalPages = 1;
        }

        if (page > totalPages) {
            page = totalPages;
        }

        List<User_24133073> users =
                userService.findAll(
                        page,
                        PAGE_SIZE
                );

        request.setAttribute("users", users);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalUsers", totalUsers);

        request.getRequestDispatcher(
                "/WEB-INF/views/admin/users/list.jsp"
        ).forward(request, response);
    }

    private void showCreateForm(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("formAction", "create");

        request.getRequestDispatcher(
                "/WEB-INF/views/admin/users/form.jsp"
        ).forward(request, response);
    }

    private void showEditForm(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String username =
                normalize(request.getParameter("username"));

        User_24133073 user =
                userService.findByUsername(username);

        if (user == null) {

            response.sendError(
                    HttpServletResponse.SC_NOT_FOUND,
                    "Không tìm thấy người dùng."
            );

            return;
        }

        request.setAttribute("user", user);
        request.setAttribute("formAction", "edit");

        request.getRequestDispatcher(
                "/WEB-INF/views/admin/users/form.jsp"
        ).forward(request, response);
    }

    private void createUser(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        User_24133073 user =
                readUserFromRequest(request);

        String confirmPassword =
                request.getParameter("confirmPassword");

        if (!isValidCreateUser(
                user,
                confirmPassword)) {

            forwardFormWithError(
                    request,
                    response,
                    user,
                    "Vui lòng nhập đầy đủ thông tin "
                            + "và xác nhận đúng mật khẩu.",
                    "create"
            );

            return;
        }

        if (userService.existsByUsername(
                user.getUsername())) {

            forwardFormWithError(
                    request,
                    response,
                    user,
                    "Tên đăng nhập đã tồn tại.",
                    "create"
            );

            return;
        }

        if (userService.existsByEmail(
                user.getEmail())) {

            forwardFormWithError(
                    request,
                    response,
                    user,
                    "Email đã được sử dụng.",
                    "create"
            );

            return;
        }

        boolean selectedAdmin =
                user.isAdmin();

        boolean selectedActive =
                user.isActive();

        boolean created =
                userService.register(user);

        if (!created) {

            forwardFormWithError(
                    request,
                    response,
                    user,
                    "Không thể thêm người dùng.",
                    "create"
            );

            return;
        }

        User_24133073 createdUser =
                userService.findByUsername(
                        user.getUsername()
                );

        createdUser.setAdmin(selectedAdmin);
        createdUser.setActive(selectedActive);
        createdUser.setImages(user.getImages());

        userService.update(createdUser);

        response.sendRedirect(
                request.getContextPath()
                        + "/admin/users?success=created"
        );
    }

    private void updateUser(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        User_24133073 user =
                readUserFromRequest(request);

        User_24133073 oldUser =
                userService.findByUsername(
                        user.getUsername()
                );

        if (oldUser == null) {

            response.sendError(
                    HttpServletResponse.SC_NOT_FOUND,
                    "Không tìm thấy người dùng."
            );

            return;
        }

        if (user.getFullname() == null
                || user.getFullname().isBlank()
                || user.getEmail() == null
                || user.getEmail().isBlank()) {

            forwardFormWithError(
                    request,
                    response,
                    user,
                    "Họ tên và email không được để trống.",
                    "edit"
            );

            return;
        }

        User_24133073 emailOwner =
                userService.findByEmail(
                        user.getEmail()
                );

        if (emailOwner != null
                && !emailOwner.getUsername().equals(
                        user.getUsername())) {

            forwardFormWithError(
                    request,
                    response,
                    user,
                    "Email đã được người dùng khác sử dụng.",
                    "edit"
            );

            return;
        }

        if (user.getImages() == null
                || user.getImages().isBlank()) {

            user.setImages(oldUser.getImages());
        }

        boolean updated =
                userService.update(user);

        if (!updated) {

            forwardFormWithError(
                    request,
                    response,
                    user,
                    "Không thể cập nhật người dùng.",
                    "edit"
            );

            return;
        }

        response.sendRedirect(
                request.getContextPath()
                        + "/admin/users?success=updated"
        );
    }

    private void deleteUser(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        String username =
                normalize(request.getParameter("username"));

        HttpSession session =
                request.getSession(false);

        User_24133073 currentUser =
                session == null
                        ? null
                        : (User_24133073)
                        session.getAttribute(
                                "currentUser"
                        );

        if (currentUser != null
                && currentUser.getUsername().equals(
                        username)) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/admin/users?error=self-delete"
            );

            return;
        }

        boolean deleted =
                userService.delete(username);

        String result =
                deleted
                        ? "success=deleted"
                        : "error=delete-failed";

        response.sendRedirect(
                request.getContextPath()
                        + "/admin/users?"
                        + result
        );
    }

    private User_24133073 readUserFromRequest(
            HttpServletRequest request) {

        User_24133073 user =
                new User_24133073();

        user.setUsername(
                normalize(
                        request.getParameter("username")
                )
        );

        user.setPassword(
                request.getParameter("password")
        );

        user.setPhone(
                normalize(
                        request.getParameter("phone")
                )
        );

        user.setFullname(
                normalize(
                        request.getParameter("fullname")
                )
        );

        user.setEmail(
                normalize(
                        request.getParameter("email")
                )
        );

        user.setImages(
                normalize(
                        request.getParameter("images")
                )
        );

        user.setAdmin(
                request.getParameter("admin") != null
        );

        user.setActive(
                request.getParameter("active") != null
        );

        return user;
    }

    private boolean isValidCreateUser(
            User_24133073 user,
            String confirmPassword) {

        return user.getUsername() != null
                && !user.getUsername().isBlank()
                && user.getPassword() != null
                && user.getPassword().length() >= 6
                && user.getPassword().equals(
                        confirmPassword
                )
                && user.getFullname() != null
                && !user.getFullname().isBlank()
                && user.getEmail() != null
                && !user.getEmail().isBlank();
    }

    private void forwardFormWithError(
            HttpServletRequest request,
            HttpServletResponse response,
            User_24133073 user,
            String error,
            String formAction)
            throws ServletException, IOException {

        request.setAttribute("user", user);
        request.setAttribute("error", error);
        request.setAttribute(
                "formAction",
                formAction
        );

        request.getRequestDispatcher(
                "/WEB-INF/views/admin/users/form.jsp"
        ).forward(request, response);
    }

    private int parsePage(String value) {

        try {
            int page = Integer.parseInt(value);

            return page > 0 ? page : 1;

        } catch (NumberFormatException e) {
            return 1;
        }
    }

    private String normalize(String value) {

        if (value == null) {
            return null;
        }

        return value.trim();
    }
}