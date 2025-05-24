package database_teamproject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class db_test {
    public static void main(String[] args) {
        try (Connection conn = sql_connection.getConnection()) {
            if (conn != null) {
                System.out.println("DB 연결 성공");

                // 테스트: 케이크 재고 현황 조회
                String sql = "SELECT c.cake_name, i.quantity_available " +
                             "FROM cake c JOIN inventory i ON c.cake_id = i.cake_id";

                try (PreparedStatement pstmt = conn.prepareStatement(sql);
                     ResultSet rs = pstmt.executeQuery()) {
                    
                    while (rs.next()) {
                        String cakeName = rs.getString("cake_name");
                        int quantity = rs.getInt("quantity_available");
                        System.out.println(cakeName + ": " + quantity + "개");
                    }

                }
            } else {
                System.out.println("DB 연결 실패");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
