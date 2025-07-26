import static spark.Spark.*;
import java.sql.*;

public class Test2 {
    public static void main(String[] args) {
        // Configure for both web and console
        port(8080);
        
        // Database credentials (replace with yours)
        final String DB_URL = "jdbc:postgresql://dpg-d21jko7gi27c73e0jqog-a.singapore-postgres.render.com:5432/musedb_ue1o";
        final String DB_USER = "musedb_ue1o_user";
        final String DB_PASSWORD = "pxHw8qDZSXZA2Rxi8lrxOtuzOnrPYBUq";

        // Console header
        System.out.println("🔊 Starting in DUAL MODE (Web + Console)");
        System.out.println("🌐 Web interface: http://localhost:8080");
        System.out.println("📋 Console output:\n");

        get("/", (req, res) -> {
            StringBuilder html = new StringBuilder()
                .append("<!DOCTYPE html><html><head><title>MuseDB</title>")
                .append("<style>body { font-family: Arial; margin: 2em; }</style>")
                .append("</head><body><h1>🎵 Song List</h1><ul>");

            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT title, singer, youtubelink FROM songs")) {
                
                // Process results
                while (rs.next()) {
                    String title = rs.getString("title");
                    String singer = rs.getString("singer");
                    String youtube = rs.getString("youtubelink");

                    // Add to HTML
                    html.append("<li><b>").append(title).append("</b> by ")
                        .append(singer).append(" <a href='").append(youtube).append("'>🎥</a></li>");

                    // Print to console
                    System.out.println("▶ " + title);
                    System.out.println("   👩🎤 " + singer);
                    System.out.println("   🔗 " + youtube);
                    System.out.println("―――――――――――――――――――");
                }

            } catch (Exception e) {
                String error = "❌ Error: " + e.getMessage();
                html.append("</ul><p style='color:red'>").append(error).append("</p>");
                System.err.println(error);
                res.status(500);
            }

            return html.append("</ul></body></html>").toString();
        });
    }
}