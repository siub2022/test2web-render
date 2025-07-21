import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class MusicApp {
    public static void main(String[] args) {
        // JDBC connection using local tunnel IP and port
        String jdbcUrl = "jdbc:postgresql://127.24.1.1:5432/_0f643bb57735"
                       + "?user=_3e3a1099e31d667a"
                       + "&password=_7a6e6be3ed1779b5200670a86237d7"
                       + "&sslmode=require";

        try (Connection conn = DriverManager.getConnection(jdbcUrl)) {
            System.out.println("🎉 Connected to db1 successfully via tunnel!");

            // Simple validation query
            String query = "SELECT current_database(), current_user;";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {

                while (rs.next()) {
                    System.out.println("📌 Database: " + rs.getString(1));
                    System.out.println("👤 User: " + rs.getString(2));
                }
            }

        } catch (SQLException e) {
            System.err.println("❌ Connection failed!");
            e.printStackTrace(); // ✅ fixed method call
        }
    }
}
