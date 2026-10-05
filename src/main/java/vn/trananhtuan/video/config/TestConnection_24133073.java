package vn.trananhtuan.video.config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TestConnection_24133073 {

    public static void main(String[] args) {

        String sql = """
                SELECT DB_NAME() AS DatabaseName,
                       Username,
                       Password,
                       Admin,
                       Active
                FROM Users
                WHERE Username = ?
                """;

        try (Connection connection =
                     DBConnection_24133073.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, "admin");

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {

                    System.out.println(
                            "KẾT NỐI DATABASE THÀNH CÔNG");

                    System.out.println(
                            "Database: "
                            + resultSet.getString("DatabaseName"));

                    System.out.println(
                            "Username: "
                            + resultSet.getString("Username"));

                    System.out.println(
                            "Password: "
                            + resultSet.getString("Password"));

                    System.out.println(
                            "Admin: "
                            + resultSet.getBoolean("Admin"));

                    System.out.println(
                            "Active: "
                            + resultSet.getBoolean("Active"));

                } else {

                    System.out.println(
                            "Kết nối được nhưng không tìm thấy admin.");
                }
            }

        } catch (Exception exception) {

            System.err.println(
                    "KẾT NỐI DATABASE THẤT BẠI");

            exception.printStackTrace();
        }
    }
}