package vn.trananhtuan.video.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vn.trananhtuan.video.config.DBConnection_24133073;
import vn.trananhtuan.video.model.User_24133073;

public class UserDAOImpl_24133073 implements IUserDAO_24133073 {

    @Override
    public User_24133073 findByUsername(String username) {

        String sql = """
                SELECT Username, Password, Phone, Fullname,
                       Email, Admin, Active, Images
                FROM Users
                WHERE Username = ?
                """;

        try (Connection connection =
                     DBConnection_24133073.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, username);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return mapUser(resultSet);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public User_24133073 findByEmail(String email) {

        String sql = """
                SELECT Username, Password, Phone, Fullname,
                       Email, Admin, Active, Images
                FROM Users
                WHERE Email = ?
                """;

        try (Connection connection =
                     DBConnection_24133073.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, email);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return mapUser(resultSet);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<User_24133073> findAll(
            int page,
            int pageSize) {

        List<User_24133073> users = new ArrayList<>();

        String sql = """
                SELECT Username, Password, Phone, Fullname,
                       Email, Admin, Active, Images
                FROM Users
                ORDER BY Username
                OFFSET ? ROWS
                FETCH NEXT ? ROWS ONLY
                """;

        int offset = (page - 1) * pageSize;

        try (Connection connection =
                     DBConnection_24133073.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, offset);
            statement.setInt(2, pageSize);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    users.add(mapUser(resultSet));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public int count() {

        String sql = "SELECT COUNT(*) FROM Users";

        try (Connection connection =
                     DBConnection_24133073.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public boolean insert(User_24133073 user) {

        String sql = """
                INSERT INTO Users
                    (Username, Password, Phone, Fullname,
                     Email, Admin, Active, Images)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection =
                     DBConnection_24133073.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            setUserParameters(statement, user);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean update(User_24133073 user) {

        String sql = """
                UPDATE Users
                SET Password = ?,
                    Phone = ?,
                    Fullname = ?,
                    Email = ?,
                    Admin = ?,
                    Active = ?,
                    Images = ?
                WHERE Username = ?
                """;

        try (Connection connection =
                     DBConnection_24133073.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, user.getPassword());
            statement.setString(2, user.getPhone());
            statement.setString(3, user.getFullname());
            statement.setString(4, user.getEmail());
            statement.setBoolean(5, user.isAdmin());
            statement.setBoolean(6, user.isActive());
            statement.setString(7, user.getImages());
            statement.setString(8, user.getUsername());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean delete(String username) {

        String sql = "DELETE FROM Users WHERE Username = ?";

        try (Connection connection =
                     DBConnection_24133073.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, username);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean existsByUsername(String username) {
        return findByUsername(username) != null;
    }

    @Override
    public boolean existsByEmail(String email) {
        return findByEmail(email) != null;
    }

    private User_24133073 mapUser(
            ResultSet resultSet) throws SQLException {

        User_24133073 user = new User_24133073();

        user.setUsername(
                resultSet.getString("Username"));
        user.setPassword(
                resultSet.getString("Password"));
        user.setPhone(
                resultSet.getString("Phone"));
        user.setFullname(
                resultSet.getString("Fullname"));
        user.setEmail(
                resultSet.getString("Email"));
        user.setAdmin(
                resultSet.getBoolean("Admin"));
        user.setActive(
                resultSet.getBoolean("Active"));
        user.setImages(
                resultSet.getString("Images"));

        return user;
    }

    private void setUserParameters(
            PreparedStatement statement,
            User_24133073 user) throws SQLException {

        statement.setString(1, user.getUsername());
        statement.setString(2, user.getPassword());
        statement.setString(3, user.getPhone());
        statement.setString(4, user.getFullname());
        statement.setString(5, user.getEmail());
        statement.setBoolean(6, user.isAdmin());
        statement.setBoolean(7, user.isActive());
        statement.setString(8, user.getImages());
    }
}