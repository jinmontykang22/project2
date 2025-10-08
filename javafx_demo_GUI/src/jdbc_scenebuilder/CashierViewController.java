package jdbc_scenebuilder;

import java.sql.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class CashierViewController {

    @FXML
    private Button queryButton; //match the fx:id value from Scene Builder
    
    @FXML
    private TextArea resultArea; //match the fx:id value from Scene Builder
    
    @FXML
    private Button closeButton; //match the fx:id value from Scene Builder

    @FXML
    private Button drinkBtn1; //match the fx:id value from Scene Builder
    @FXML
    private Button drinkBtn2; //match the fx:id value from Scene Builder
    @FXML
    private Button drinkBtn3; //match the fx:id value from Scene Builder
    @FXML
    private Button drinkBtn4; //match the fx:id value from Scene Builder
    @FXML
    private Button drinkBtn5; //match the fx:id value from Scene Builder
    @FXML
    private Button drinkBtn6; //match the fx:id value from Scene Builder
    @FXML
    private Button drinkBtn7; //match the fx:id value from Scene Builder
    @FXML
    private Button drinkBtn8; //match the fx:id value from Scene Builder
    @FXML
    private Button drinkBtn9; //match the fx:id value from Scene Builder
    @FXML
    private Button drinkBtn10; //match the fx:id value from Scene Builder
    @FXML
    private Button drinkBtn11; //match the fx:id value from Scene Builder
    @FXML
    private Button drinkBtn12; //match the fx:id value from Scene Builder

    @FXML
    private TextArea orderSumArea; //match the fx:id value from Scene Builder

    @FXML
    private void switchToManagerView(ActionEvent event) throws Exception {
        ViewApp.changeScene("manager-view.fxml");
    }
    
    private static final String DB_URL = "jdbc:postgresql://csce-315-db.engr.tamu.edu/gang_52_db"; //database location
    
    // This method runs automatically when the FXML loads
    @FXML
    public void initialize() {
        // Set up what happens when button is clicked
        queryButton.setOnAction(event -> runQuery("SELECT product_name FROM products"));
        closeButton.setOnAction(event -> closeWindow());

        //drinkBtn1.setText("Classic Milk Tea");

        String productNames = runQuery("SELECT product_name FROM products");
        for(int i = 1; i < 26; i++) {
            String name = productNames.split(",")[i-1];
            switch(i) {
                case 1: drinkBtn1.setText(name); break;
                case 2: drinkBtn2.setText(name); break;
                case 3: drinkBtn3.setText(name); break;
                case 4: drinkBtn4.setText(name); break;
                case 5: drinkBtn5.setText(name); break;
                case 6: drinkBtn6.setText(name); break;
                case 7: drinkBtn7.setText(name); break;
                case 8: drinkBtn8.setText(name); break;
                case 9: drinkBtn9.setText(name); break;
                case 10: drinkBtn10.setText(name); break;
                case 11: drinkBtn11.setText(name); break;
                case 12: drinkBtn12.setText(name); break;
                default: break; //do nothing
            }
            
        }

        //SELECT product_name FROM products WHERE product_id = i

        drinkBtn1.setOnAction(event -> selectDrinkBtn(drinkBtn1.getText()));
        drinkBtn2.setOnAction(event -> selectDrinkBtn(drinkBtn2.getText()));
        drinkBtn3.setOnAction(event -> selectDrinkBtn(drinkBtn3.getText()));
        drinkBtn4.setOnAction(event -> selectDrinkBtn(drinkBtn4.getText()));
        drinkBtn5.setOnAction(event -> selectDrinkBtn(drinkBtn5.getText()));
        drinkBtn6.setOnAction(event -> selectDrinkBtn(drinkBtn6.getText()));
        drinkBtn7.setOnAction(event -> selectDrinkBtn(drinkBtn7.getText()));
        drinkBtn8.setOnAction(event -> selectDrinkBtn(drinkBtn8.getText()));
        drinkBtn9.setOnAction(event -> selectDrinkBtn(drinkBtn9.getText()));
        drinkBtn10.setOnAction(event -> selectDrinkBtn(drinkBtn10.getText()));
        drinkBtn11.setOnAction(event -> selectDrinkBtn(drinkBtn11.getText()));
        drinkBtn12.setOnAction(event -> selectDrinkBtn(drinkBtn12.getText()));
    }

    //method to select a drink and add it to the Order Summary display
    private void selectDrinkBtn(String drinkBtnStr) {
        String drinkName = drinkBtnStr;
        String drinkPrice = "7.00"; //harcoded for demo purposes

        // Append the selected drink to the Order Summary
        String currentText = orderSumArea.getText();
        orderSumArea.setText(currentText + drinkName + " - " + drinkPrice + "\n");
    }
    
    // Your method to run the database query
    private String runQuery(String sqlStatement) {
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
            //String sqlStatement = "SELECT product_name FROM products";
            ResultSet rs = stmt.executeQuery(sqlStatement);

            // Output result
            String result = "";
            while (rs.next()) {
                result += rs.getString("product_name") + ",";
            }

            // Display result
            //resultArea.setText(result);

            // Close connection
            rs.close();
            stmt.close();
            conn.close();

            return result;

        } catch (Exception e) {
            resultArea.setText("Error connecting to database:\n" + e.getMessage());
            e.printStackTrace();
            System.exit(0);

            return "ERROR";
        }
    }

    private void closeWindow() { 
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
