package vn.trananhtuan.video.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import vn.trananhtuan.video.dao.OrderDAOImpl_24133073;
import vn.trananhtuan.video.model.Order_24133073;
import vn.trananhtuan.video.model.User_24133073;

@WebServlet("/orders")
public class OrderHistoryController_24133073 extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private OrderDAOImpl_24133073 orderDAO = new OrderDAOImpl_24133073();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User_24133073 user = (User_24133073) session.getAttribute("currentUser");
        
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Lấy status filter từ URL (nếu có)
        String status = request.getParameter("status");
        if (status == null || status.isBlank()) {
            status = "All";
        }

        List<Order_24133073> orders = orderDAO.getOrdersByUsernameAndStatus(user.getUsername(), status);
        
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("/WEB-INF/views/orders.jsp").forward(request, response);
    }
}