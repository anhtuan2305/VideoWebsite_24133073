package vn.trananhtuan.video.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import vn.trananhtuan.video.config.DBConnection_24133073;
import vn.trananhtuan.video.model.Category_24133073;

public class CategoryDAOImpl_24133073
        implements ICategoryDAO_24133073 {

    @Override
    public Category_24133073 findById(int categoryId) {

        String sql = """
                SELECT CategoryId, Categoryname,
                       Categorycode, Images, Status
                FROM Category
                WHERE CategoryId = ?
                """;

        try (Connection connection =
                     DBConnection_24133073.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, categoryId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return mapCategory(resultSet);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Category_24133073> findAll() {

        List<Category_24133073> categories =
                new ArrayList<>();

        String sql = """
                SELECT CategoryId, Categoryname,
                       Categorycode, Images, Status
                FROM Category
                ORDER BY CategoryId
                """;

        try (Connection connection =
                     DBConnection_24133073.getConnection();
             Statement statement =
                     connection.createStatement();
             ResultSet resultSet =
                     statement.executeQuery(sql)) {

            while (resultSet.next()) {
                categories.add(mapCategory(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }

    @Override
    public List<Category_24133073> findAll(
            int page,
            int pageSize) {

        List<Category_24133073> categories =
                new ArrayList<>();

        String sql = """
                SELECT CategoryId, Categoryname,
                       Categorycode, Images, Status
                FROM Category
                ORDER BY CategoryId
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
                    categories.add(mapCategory(resultSet));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }

    @Override
    public int count() {

        String sql = "SELECT COUNT(*) FROM Category";

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
    public boolean insert(Category_24133073 category) {

        String sql = """
                INSERT INTO Category
                    (Categoryname, Categorycode, Images, Status)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection connection =
                     DBConnection_24133073.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(
                    1, category.getCategoryName());
            statement.setString(
                    2, category.getCategoryCode());
            statement.setString(
                    3, category.getImages());
            statement.setBoolean(
                    4, category.isStatus());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean update(Category_24133073 category) {

        String sql = """
                UPDATE Category
                SET Categoryname = ?,
                    Categorycode = ?,
                    Images = ?,
                    Status = ?
                WHERE CategoryId = ?
                """;

        try (Connection connection =
                     DBConnection_24133073.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(
                    1, category.getCategoryName());
            statement.setString(
                    2, category.getCategoryCode());
            statement.setString(
                    3, category.getImages());
            statement.setBoolean(
                    4, category.isStatus());
            statement.setInt(
                    5, category.getCategoryId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean delete(int categoryId) {

        String sql =
                "DELETE FROM Category WHERE CategoryId = ?";

        try (Connection connection =
                     DBConnection_24133073.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, categoryId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private Category_24133073 mapCategory(
            ResultSet resultSet) throws SQLException {

        Category_24133073 category =
                new Category_24133073();

        category.setCategoryId(
                resultSet.getInt("CategoryId"));
        category.setCategoryName(
                resultSet.getString("Categoryname"));
        category.setCategoryCode(
                resultSet.getString("Categorycode"));
        category.setImages(
                resultSet.getString("Images"));
        category.setStatus(
                resultSet.getBoolean("Status"));

        return category;
    }
}