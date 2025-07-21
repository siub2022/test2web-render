import static spark.Spark.*; // Import Spark library for lightweight web server

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * SongServer2 sets up a backend service using Spark,
 * connects to a PostgreSQL database (db1), and returns
 * an HTML page listing song titles from the "songs" table.
 * 
 * This version is annotated with JavaDoc and inline comments
 * for instructional clarity and documentation purposes.
 */
public class SongServer2 {

    /**
     * Main method initializes server on port 4567.
     * It defines an HTTP GET route, connects to the database using JDBC,
     * fetches song titles via SQL, builds a dynamic HTML response,
     * and serves it to the client's browser.
     *
     * @param args Command-line arguments (not used)
     */
    public static void main(String[] args) {

        // Launch Spark web server on local port 4567
        port(4567);

        // Define GET route at "/" that returns an HTML page
        get("/", (req, res) -> {

            // Create HTML structure using a StringBuilder
            StringBuilder html = new StringBuilder();
            html.append("<!DOCTYPE html>");
            html.append("<html lang=\"en\">");
            html.append("<head>");
            html.append("<meta charset=\"UTF-8\">");
            html.append("<title>Song List</title>");
            html.append("</head>");
            html.append("<body>");
            html.append("<h1>List of Songs in db1</h1>");
            html.append("<ul>");

            try {
                // Create JDBC connection to local PostgreSQL database "db1"

Connection conn = DriverManager.getConnection(
    "jdbc:postgresql://primary.db1--mq4mtqj9mcvl.addon.code.run:5432/_0f643bb57735?sslmode=require",
    "_3e3a1099e31d667a",
    "_7a6e6be3ed1779b5200670a86237d7"
);

                // Create and execute SQL query
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT title FROM songs");

                // Process query result: add each song title to HTML list
                while (rs.next()) {
                    String title = rs.getString("title");
                    html.append("<li>").append(title).append("</li>");
                }

                // Release database resources
                rs.close();
                stmt.close();
                conn.close();

            } catch (Exception e) {
                // If error occurs, build HTML error message instead
                html.append("</ul>");
                html.append("<p><strong>Error loading songs:</strong> ")
                    .append(e.getMessage()).append("</p>");
                html.append("</body></html>");
                res.status(500); // Send HTTP 500 status for server error
                return html.toString();
            }

            // Finalize the HTML response
            html.append("</ul>");
            html.append("</body>");
            html.append("</html>");

            // Specify content type and return response
            res.type("text/html");
            return html.toString();
        });
    }
}
