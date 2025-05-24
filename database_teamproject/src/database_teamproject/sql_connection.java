package database_teamproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class sql_connection {
    private static final String URL = "jdbc:mysql://localhost:3306/cake1"; // DB 이름 확인!
    private static final String USER = "root"; // 사용자 이름
    private static final String PASSWORD = "estherpark1017m"; // 비밀번호

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
