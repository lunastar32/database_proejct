package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class sql_connection {
  private static final String URL = "jdbc:mysql://localhost:3306/cake1";
  private static final String USER = "root";
  private static final String PASSWORD = "estherpark1017m";

  private static Connection conn = null;

  public sql_connection() {
  }

  public static Connection getConnection() {
    if(conn == null) {
      try {
         Class.forName("com.mysql.cj.jdbc.Driver");
         conn = DriverManager.getConnection(URL, USER, PASSWORD);
         System.out.println("DB 연결 성공");
      } catch (SQLException | ClassNotFoundException var1) {
        System.out.println("DB 연결 실패"); 
        var1.printStackTrace();
      }
    }
    return conn;
  }

  public static void close(){
    try{
      if(conn != null && !conn.isClosed()){
        conn.close();
        System.out.println("DB 연결 종료");
      }
    } catch(SQLException e){
      e.printStackTrace();
    }
  }
}
