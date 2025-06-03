package database;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Statement;

public class SQLExecutor {
    public static void executeSQLFile(Connection conn, String filePath) {
        try {
            String sql = new String(Files.readAllBytes(Paths.get(filePath)));
            String[] queries = sql.split(";");

            Statement stmt = conn.createStatement();
            for (String query : queries) {
                query = query.trim();
                if (!query.isEmpty()) {
                    stmt.execute(query);
                }
            }

            System.out.println("✅ SQL 실행 완료: " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
