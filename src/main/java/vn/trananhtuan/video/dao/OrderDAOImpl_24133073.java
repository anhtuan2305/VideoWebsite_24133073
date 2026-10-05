package vn.trananhtuan.video.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import vn.trananhtuan.video.config.DBConnection_24133073;
import vn.trananhtuan.video.model.CartItem_24133073;
import vn.trananhtuan.video.model.Order_24133073;

public class OrderDAOImpl_24133073 {

    // Hàm chèn đơn hàng mới (Thanh toán COD)
    public boolean insertOrder(String username, double totalAmount, String address, String phone, List<CartItem_24133073> cart) {
        String sqlOrder = "INSERT INTO Orders (Username, TotalAmount, PaymentMethod, Status, ShippingAddress, Phone) VALUES (?, ?, 'COD', N'Mới đặt', ?, ?)";
        String sqlDetail = "INSERT INTO OrderDetails (OrderId, VideoId, Quantity, Price) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DBConnection_24133073.getConnection()) {
            conn.setAutoCommit(false); // Bắt đầu Transaction
            
            try (PreparedStatement psOrder = conn.prepareStatement(sqlOrder, Statement.RETURN_GENERATED_KEYS)) {
                psOrder.setString(1, username);
                psOrder.setDouble(2, totalAmount);
                psOrder.setString(3, address);
                psOrder.setString(4, phone);
                psOrder.executeUpdate();
                
                ResultSet rs = psOrder.getGeneratedKeys();
                int orderId = 0;
                if (rs.next()) {
                    orderId = rs.getInt(1);
                }
                
                try (PreparedStatement psDetail = conn.prepareStatement(sqlDetail)) {
                    for (CartItem_24133073 item : cart) {
                        psDetail.setInt(1, orderId);
                        psDetail.setString(2, item.getVideoId());
                        psDetail.setInt(3, item.getQuantity());
                        psDetail.setDouble(4, item.getPrice());
                        psDetail.addBatch();
                    }
                    psDetail.executeBatch();
                }
                
                conn.commit(); // Thành công thì lưu DB
                return true;
            } catch (Exception e) {
                conn.rollback(); // Lỗi thì hoàn tác
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Hàm lấy danh sách đơn hàng theo trạng thái để lọc
    public List<Order_24133073> getOrdersByUsernameAndStatus(String username, String status) {
        List<Order_24133073> list = new ArrayList<>();
        String sql = "SELECT * FROM Orders WHERE Username = ? ";
        if (status != null && !status.equals("All")) {
            sql += "AND Status = ? ";
        }
        sql += "ORDER BY OrderDate DESC";
        
        try (Connection conn = DBConnection_24133073.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
             
            ps.setString(1, username);
            if (status != null && !status.equals("All")) {
                ps.setString(2, status);
            }
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order_24133073 order = new Order_24133073();
                order.setOrderId(rs.getInt("OrderId"));
                order.setUsername(rs.getString("Username"));
                order.setOrderDate(rs.getTimestamp("OrderDate"));
                order.setTotalAmount(rs.getDouble("TotalAmount"));
                order.setPaymentMethod(rs.getString("PaymentMethod"));
                order.setStatus(rs.getString("Status"));
                order.setShippingAddress(rs.getString("ShippingAddress"));
                order.setPhone(rs.getString("Phone"));
                list.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}