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

@WebFilter(
        urlPatterns = {
                "/user/*",
                "/admin/*"
        }
)
public class AuthenticationFilter_24133073
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

            String loginUrl =
                    httpRequest.getContextPath()
                            + "/login";

            httpResponse.sendRedirect(loginUrl);
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Không cần giải phóng tài nguyên
    }
}