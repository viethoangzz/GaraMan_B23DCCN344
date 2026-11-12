package vn.edu.ptit.garaman.dao;

import java.sql.*;

public class DAO4 {
    private static String env(String key, String defaultValue) {
        String value = System.getenv(key);
        return value == null || value.isBlank() ? defaultValue : value;
    }
    public Connection getConnection() throws SQLException {
        // Load the driver through the web application's class loader on Tomcat.
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            throw new SQLException("MySQL JDBC driver is missing", ex);
        }
        return DriverManager.getConnection(
            env("DB_URL", "jdbc:mysql://localhost:3306/garaman?useUnicode=true&characterEncoding=UTF-8&connectionTimeZone=SERVER"),
            env("DB_USER", "root"), env("DB_PASSWORD", ""));
    }
    protected static void setDateRange(PreparedStatement ps, int index, java.time.LocalDate start, java.time.LocalDate end) throws SQLException {
        ps.setTimestamp(index, Timestamp.valueOf(start.atStartOfDay()));
        ps.setTimestamp(index + 1, Timestamp.valueOf(end.plusDays(1).atStartOfDay()));
    }
    public static String likeContains(String keyword) {
        String k = keyword == null ? "" : keyword.trim();
        k = k.replace("\\", "\\\\").replace("%", "\\%").replace("_", "\\_");
        return "%" + k + "%";
    }
}
