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
import vn.trananhtuan.video.model.CartItem_24133073;
import vn.trananhtuan.video.model.User_24133073; // (giữ nguyên package của ông)

@WebServlet("/checkout")
public class CheckoutController_24133073 extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private OrderDAOImpl_24133073 orderDAO = new OrderDAOImpl_24133073();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<CartItem_24133073> cart = (List<CartItem_24133073>) session.getAttribute("cart");
        
        if (cart == null || cart.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        // Hiển thị trang checkout.jsp độc lập
        request.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        
        User_24133073 user = (User_24133073) session.getAttribute("currentUser");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        List<CartItem_24133073> cart = (List<CartItem_24133073>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        
        double totalAmount = 0;
        for (CartItem_24133073 item : cart) {
            totalAmount += item.getTotalPrice();
        }

        boolean success = orderDAO.insertOrder(user.getUsername(), totalAmount, address, phone, cart);
        
        if (success) {
            session.removeAttribute("cart");
            response.sendRedirect(request.getContextPath() + "/orders?success=true");
        } else {
            response.sendRedirect(request.getContextPath() + "/cart?error=true");
        }
    }
}