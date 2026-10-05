package vn.trananhtuan.video.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class DBConnection_24133073 {

    private static final Properties PROPERTIES =
            new Properties();

    private DBConnection_24133073() {
    }

    static {
        try (InputStream inputStream =
                     DBConnection_24133073.class
                             .getClassLoader()
                             .getResourceAsStream(
                                     "db.properties")) {

            if (inputStream == null) {
                throw new IllegalStateException(
                        "Không tìm thấy file db.properties."
                );
            }

            PROPERTIES.load(inputStream);

            Class.forName(
                    PROPERTIES.getProperty("db.driver")
            );

        } catch (IOException |
                 ClassNotFoundException exception) {

            throw new ExceptionInInitializerError(
                    exception
            );
        }
    }

    public static Connection getConnection()
            throws SQLException {

        return DriverManager.getConnection(
                PROPERTIES.getProperty("db.url"),
                PROPERTIES.getProperty("db.username"),
                PROPERTIES.getProperty("db.password")
        );
    }
}