package vn.trananhtuan.video.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vn.trananhtuan.video.config.DBConnection_24133073;
import vn.trananhtuan.video.model.Video_24133073;

public class VideoDAOImpl_24133073
        implements IVideoDAO_24133073 {

    @Override
    public Video_24133073 findById(String videoId) {

        String sql = """
                SELECT v.VideoId, v.Title, v.Poster,
                       v.Views, v.Description, v.Active,
                       v.CategoryId, c.Categoryname
                FROM Videos v
                INNER JOIN Category c
                    ON v.CategoryId = c.CategoryId
                WHERE v.VideoId = ?
                """;

        try (Connection connection =
                     DBConnection_24133073.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, videoId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return mapVideo(resultSet);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Video_24133073> findAll(
            int page,
            int pageSize) {

        List<Video_24133073> videos =
                new ArrayList<>();

        String sql = """
                SELECT v.VideoId, v.Title, v.Poster,
                       v.Views, v.Description, v.Active,
                       v.CategoryId, c.Categoryname
                FROM Videos v
                INNER JOIN Category c
                    ON v.CategoryId = c.CategoryId
                ORDER BY v.VideoId
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
                    videos.add(mapVideo(resultSet));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return videos;
    }

    @Override
    public List<Video_24133073> findByCategory(
            int categoryId,
            int page,
            int pageSize) {

        List<Video_24133073> videos =
                new ArrayList<>();

        String sql = """
                SELECT v.VideoId, v.Title, v.Poster,
                       v.Views, v.Description, v.Active,
                       v.CategoryId, c.Categoryname
                FROM Videos v
                INNER JOIN Category c
                    ON v.CategoryId = c.CategoryId
                WHERE v.CategoryId = ?
                  AND v.Active = 1
                ORDER BY v.VideoId
                OFFSET ? ROWS
                FETCH NEXT ? ROWS ONLY
                """;

        int offset = (page - 1) * pageSize;

        try (Connection connection =
                     DBConnection_24133073.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, categoryId);
            statement.setInt(2, offset);
            statement.setInt(3, pageSize);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    videos.add(mapVideo(resultSet));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return videos;
    }

    @Override
    public int count() {

        String sql = "SELECT COUNT(*) FROM Videos";

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
    public int countByCategory(int categoryId) {

        String sql = """
                SELECT COUNT(*)
                FROM Videos
                WHERE CategoryId = ?
                  AND Active = 1
                """;

        try (Connection connection =
                     DBConnection_24133073.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, categoryId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public int countLikes(String videoId) {

        String sql = """
                SELECT COUNT(*)
                FROM Favorites
                WHERE VideoId = ?
                """;

        return executeCount(sql, videoId);
    }

    @Override
    public int countShares(String videoId) {

        String sql = """
                SELECT COUNT(*)
                FROM Shares
                WHERE VideoId = ?
                """;

        return executeCount(sql, videoId);
    }

    @Override
    public boolean insert(Video_24133073 video) {

        String sql = """
                INSERT INTO Videos
                    (VideoId, Title, Poster, Views,
                     Description, Active, CategoryId)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection =
                     DBConnection_24133073.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, video.getVideoId());
            statement.setString(2, video.getTitle());
            statement.setString(3, video.getPoster());
            statement.setInt(4, video.getViews());
            statement.setString(5, video.getDescription());
            statement.setBoolean(6, video.isActive());
            statement.setInt(7, video.getCategoryId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean update(Video_24133073 video) {

        String sql = """
                UPDATE Videos
                SET Title = ?,
                    Poster = ?,
                    Views = ?,
                    Description = ?,
                    Active = ?,
                    CategoryId = ?
                WHERE VideoId = ?
                """;

        try (Connection connection =
                     DBConnection_24133073.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, video.getTitle());
            statement.setString(2, video.getPoster());
            statement.setInt(3, video.getViews());
            statement.setString(4, video.getDescription());
            statement.setBoolean(5, video.isActive());
            statement.setInt(6, video.getCategoryId());
            statement.setString(7, video.getVideoId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean delete(String videoId) {

        String deleteShares =
                "DELETE FROM Shares WHERE VideoId = ?";

        String deleteFavorites =
                "DELETE FROM Favorites WHERE VideoId = ?";

        String deleteVideo =
                "DELETE FROM Videos WHERE VideoId = ?";

        try (Connection connection =
                     DBConnection_24133073.getConnection()) {

            connection.setAutoCommit(false);

            try {
                executeDelete(
                        connection,
                        deleteShares,
                        videoId
                );

                executeDelete(
                        connection,
                        deleteFavorites,
                        videoId
                );

                int affectedRows = executeDelete(
                        connection,
                        deleteVideo,
                        videoId
                );

                connection.commit();
                return affectedRows > 0;

            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            } finally {
                connection.setAutoCommit(true);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean increaseViews(String videoId) {

        String sql = """
                UPDATE Videos
                SET Views = Views + 1
                WHERE VideoId = ?
                """;

        try (Connection connection =
                     DBConnection_24133073.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, videoId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private Video_24133073 mapVideo(
            ResultSet resultSet) throws SQLException {

        Video_24133073 video = new Video_24133073();

        video.setVideoId(
                resultSet.getString("VideoId"));
        video.setTitle(
                resultSet.getString("Title"));
        video.setPoster(
                resultSet.getString("Poster"));
        video.setViews(
                resultSet.getInt("Views"));
        video.setDescription(
                resultSet.getString("Description"));
        video.setActive(
                resultSet.getBoolean("Active"));
        video.setCategoryId(
                resultSet.getInt("CategoryId"));
        video.setCategoryName(
                resultSet.getString("Categoryname"));

        return video;
    }

    private int executeCount(
            String sql,
            String videoId) {

        try (Connection connection =
                     DBConnection_24133073.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, videoId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private int executeDelete(
            Connection connection,
            String sql,
            String videoId) throws SQLException {

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, videoId);
            return statement.executeUpdate();
        }
    }
}