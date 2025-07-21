import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MusicApp {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://127.24.1.1:5432/_0f643bb57735"
                   + "?user=_3e3a1099e31d667a"
                   + "&password=_7a6e6be3ed1779b5200670a86237d7"
                   + "&sslmode=require";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT title, singer, youtubelink FROM songs")) {

            System.out.println("🎶 Songs in your database:");
            while (rs.next()) {
                String title = rs.getString("title");
                String singer = rs.getString("singer");
                String link = rs.getString("youtubelink");
                System.out.println("- " + title + " by " + singer);
                System.out.println("  🎥 Watch: " + link);
            }

        } catch (Exception e) {
            System.out.println("❌ Connection or query failed!");
            e.printStackTrace();
        }
    }
}
