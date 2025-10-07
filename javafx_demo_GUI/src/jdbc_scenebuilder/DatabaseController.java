package jdbc_scenebuilder;

import java.sql.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class DatabaseController {
    
    @FXML
    private Button queryButton; //match the fx:id value from Scene Builder
    
    @FXML
    private TextArea resultArea; //match the fx:id value from Scene Builder
    
    @FXML
    private Button closeButton; //match the fx:id value from Scene Builder
    
    
    private static final String DB_URL = "jdbc:postgresql://csce-315-db.engr.tamu.edu/gang_52_db"; //database location
    
    // This method runs automatically when the FXML loads
    @FXML
    public void initialize() {
        // Set up what happens when button is clicked
        queryButton.setOnAction(event -> runQuery());
        closeButton.setOnAction(event -> closeWindow());
    }
    
    // Your method to run the database query
    private void runQuery() {
        resultArea.setText("Query will run here...");

        try {
            // Get database creditials
            dbSetup my = new dbSetup();
 
            // Build the connection
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, my.user, my.pswd);

            // Create statement
            Statement stmt = conn.createStatement();

            // Run sql query
            String sqlStatement = "SELECT total_price FROM orders LIMIT 10";
            ResultSet rs = stmt.executeQuery(sqlStatement);

            // Output result
            String result = "";
            while (rs.next()) {
                result += rs.getString("total_price") + "\n";
            }

            // Display result
            resultArea.setText(result);

            // Close connection
            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            resultArea.setText("Error connecting to database:\n" + e.getMessage());
            e.printStackTrace();
            System.exit(0);
        }
    }

    private void closeWindow() { 
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
