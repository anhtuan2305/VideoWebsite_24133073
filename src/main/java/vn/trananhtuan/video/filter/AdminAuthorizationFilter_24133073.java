package vn.trananhtuan.video.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import vn.trananhtuan.video.model.User_24133073;

@WebFilter(urlPatterns = "/admin/*")
public class AdminAuthorizationFilter_24133073
        implements Filter {

    @Override
    public void init(FilterConfig filterConfig)
            throws ServletException {

        // Không cần khởi tạo thêm
    }

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest =
                (HttpServletRequest) request;

        HttpServletResponse httpResponse =
                (HttpServletResponse) response;

        HttpSession session =
                httpRequest.getSession(false);

        User_24133073 currentUser = null;

        if (session != null) {
            currentUser =
                    (User_24133073) session.getAttribute(
                            "currentUser"
                    );
        }

        if (currentUser == null) {

            httpResponse.sendRedirect(
                    httpRequest.getContextPath()
                            + "/login"
            );

            return;
        }

        if (!currentUser.isAdmin()) {

            httpResponse.sendRedirect(
                    httpRequest.getContextPath()
                            + "/home"
            );

            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Không cần giải phóng tài nguyên
    }
}