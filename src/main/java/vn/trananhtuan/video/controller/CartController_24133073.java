package vn.trananhtuan.video.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import vn.trananhtuan.video.model.CartItem_24133073;
import vn.trananhtuan.video.model.Video_24133073;
import vn.trananhtuan.video.service.IVideoService_24133073;
import vn.trananhtuan.video.service.VideoServiceImpl_24133073;

@WebServlet(
        urlPatterns = {
                "/cart",
                "/cart/*"
        }
)
public class CartController_24133073 extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final int MAX_QUANTITY = 10;
    private static final int MIN_QUANTITY = 1;
    private static final double DEFAULT_VIDEO_PRICE = 99000.0;

    private IVideoService_24133073 videoService;

    @Override
    public void init() throws ServletException {
        videoService = new VideoServiceImpl_24133073();
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
            showCart(request, response);
            return;
        }

        response.sendError(HttpServletResponse.SC_NOT_FOUND);
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
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        switch (path) {
            case "/add":
                addToCart(request, response);
                break;

            case "/update":
                updateQuantity(request, response);
                break;

            case "/remove":
                removeItem(request, response);
                break;

            case "/clear":
                clearCart(request, response);
                break;

            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    private void showCart(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        List<CartItem_24133073> cart = getCartFromSession(session);

        double totalAmount = calculateTotalAmount(cart);

        request.setAttribute("cart", cart);
        request.setAttribute("totalAmount", totalAmount);

        request.getRequestDispatcher("/WEB-INF/views/cart.jsp")
                .forward(request, response);
    }

    private void addToCart(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        String videoId = normalize(request.getParameter("videoId"));

        if (videoId == null || videoId.isBlank()) {
            response.sendRedirect(request.getContextPath() + "/home?error=invalid-video");
            return;
        }

        Video_24133073 video = videoService.findById(videoId);

        if (video == null || !video.isActive()) {
            response.sendRedirect(request.getContextPath() + "/home?error=video-not-found");
            return;
        }

        HttpSession session = request.getSession(true);
        List<CartItem_24133073> cart = getCartFromSession(session);

        boolean exists = false;
        for (CartItem_24133073 item : cart) {
            if (item.getVideoId() != null && item.getVideoId().equalsIgnoreCase(videoId)) {
                int nextQty = item.getQuantity() + 1;
                item.setQuantity(Math.min(nextQty, MAX_QUANTITY));
                exists = true;
                break;
            }
        }

        if (!exists) {
            CartItem_24133073 newItem = new CartItem_24133073();
            newItem.setVideoId(video.getVideoId());
            newItem.setTitle(video.getTitle());
            newItem.setPoster(video.getPoster());
            newItem.setPrice(DEFAULT_VIDEO_PRICE);
            newItem.setQuantity(1);

            cart.add(newItem);
        }

        session.setAttribute("cart", cart);

        response.sendRedirect(request.getContextPath() + "/cart?success=added");
    }

    private void updateQuantity(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        String videoId = normalize(request.getParameter("videoId"));
        int quantity = parseInteger(request.getParameter("quantity"), 1);

        if (quantity > MAX_QUANTITY) {
            quantity = MAX_QUANTITY;
        } else if (quantity < MIN_QUANTITY) {
            quantity = MIN_QUANTITY;
        }

        HttpSession session = request.getSession(true);
        List<CartItem_24133073> cart = getCartFromSession(session);

        for (CartItem_24133073 item : cart) {
            if (item.getVideoId() != null && item.getVideoId().equalsIgnoreCase(videoId)) {
                item.setQuantity(quantity);
                break;
            }
        }

        session.setAttribute("cart", cart);

        response.sendRedirect(request.getContextPath() + "/cart?success=updated");
    }

    private void removeItem(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        String videoId = normalize(request.getParameter("videoId"));

        if (videoId != null && !videoId.isBlank()) {
            HttpSession session = request.getSession(true);
            List<CartItem_24133073> cart = getCartFromSession(session);

            cart.removeIf(item -> item.getVideoId() != null && item.getVideoId().equalsIgnoreCase(videoId));

            session.setAttribute("cart", cart);
        }

        response.sendRedirect(request.getContextPath() + "/cart?success=deleted");
    }

    private void clearCart(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession(true);
        session.removeAttribute("cart");

        response.sendRedirect(request.getContextPath() + "/cart");
    }

    @SuppressWarnings("unchecked")
    private List<CartItem_24133073> getCartFromSession(HttpSession session) {
        List<CartItem_24133073> cart = (List<CartItem_24133073>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    private double calculateTotalAmount(List<CartItem_24133073> cart) {
        double total = 0.0;
        if (cart != null) {
            for (CartItem_24133073 item : cart) {
                total += item.getTotalPrice();
            }
        }
        return total;
    }

    private int parseInteger(String value, int defaultValue) {
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private String normalize(String value) {
        return value == null ? null : value.trim();
    }
}