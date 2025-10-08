package jdbc_scenebuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Button categoryBtn1;

    @FXML
    private Button categoryBtn2;

    @FXML
    private Button categoryBtn3;

    @FXML
    private TextArea orderSumArea; //match the fx:id value from Scene Builder
    
    
    private static final String DB_URL = "jdbc:postgresql://csce-315-db.engr.tamu.edu/gang_52_db"; //database location

    private Button[] drinkButtons;
    private Button[] categoryButtons;
    private String[] categories;
    
    // This method runs automatically when the FXML loads
    @FXML
    public void initialize() {
        //ordering of these initialization statements not very clean but it works for now
        drinkButtons = new Button[]{drinkBtn1, drinkBtn2, drinkBtn3, drinkBtn4, drinkBtn5, drinkBtn6,
                                    drinkBtn7, drinkBtn8, drinkBtn9, drinkBtn10, drinkBtn11, drinkBtn12};

        categoryButtons = new Button[]{categoryBtn1, categoryBtn2, categoryBtn3};

        categories = new String[]{"hot", "milk", "fruit"};

        closeButton.setOnAction(event -> closeWindow());

        Map<String, List<String>> prodCatMap = createProductCategoryMap("SELECT product_name, category FROM products");

        for (Button b : drinkButtons) {
            b.setText(""); 
            b.setVisible(false); 
            b.setOnAction(event -> selectDrinkBtn(b.getText()));
        }

        for (int i = 0; i < categoryButtons.length; i++) {
            int lambda_i = i; //needs this for some reason: local variables referenced from a lambda expression must be final or effectively final
            categoryButtons[i].setText(categories[i].toUpperCase());
            categoryButtons[i].setOnAction(event -> populateByCategory(categories[lambda_i], prodCatMap));
        }

        populateByCategory(categories[0], prodCatMap);
    }

    //on button click, adds the drink to the order summary text area
    private void selectDrinkBtn(String drinkBtnStr) {
        String drinkName = drinkBtnStr;
        //may be a little slow manually running queries every time to search for the price. Need to figure out how to store the prices locally
        double drinkPrice = runQuery("SELECT price FROM products WHERE product_name = '" + drinkName + "'", "price"); 

        String currentText = orderSumArea.getText();
        orderSumArea.setText(currentText + drinkName + " - " + drinkPrice + "\n");
    }

    //populates the drink buttons based on the selected category
    private void populateByCategory(String category, Map<String, List<String>> productCategoryMap) {
        List<String> productNames = productCategoryMap.get(category);

        for (Button b : drinkButtons) {
            b.setText(""); 
            b.setVisible(false); 
        }

        for (int i = 0; i < productNames.size() && i < drinkButtons.length; i++) {
            drinkButtons[i].setText(productNames.get(i));
            drinkButtons[i].setVisible(true); 
        }
    }

    // Your method to run the database query (for now, specifically for getting the price of a drink)
    private double runQuery(String sqlStatement, String columnLabel) {
        try {
            // Get database creditials
            dbSetup my = new dbSetup();
 
            // Build the connection
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, my.user, my.pswd);

            // Create statement
            Statement stmt = conn.createStatement();

            // Run sql query
            ResultSet rs = stmt.executeQuery(sqlStatement);

            // Output result
            String result = "";
            while (rs.next()) {
                result += rs.getString(columnLabel) + ",";
            }

            // Close connection
            rs.close();
            stmt.close();
            conn.close();

            return Double.parseDouble(result.replace(",", "")); 

        } catch (Exception e) {
            resultArea.setText("Error connecting to database:\n" + e.getMessage());
            e.printStackTrace();
            System.exit(0);

            return -1;
        }
    }
    
    // Creates a map of product names to their categories. used in various places
    private Map<String, List<String>> createProductCategoryMap(String sqlStatement) {
        try {
            // Get database creditials
            dbSetup my = new dbSetup();
 
            // Build the connection
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, my.user, my.pswd);

            // Create statement
            Statement stmt = conn.createStatement();

            // Run sql query
            ResultSet rs = stmt.executeQuery(sqlStatement);

            List<String[]> productCategory = new ArrayList<>(); //no tuple in java ;-;
            while (rs.next()) {
                productCategory.add(new String[]{rs.getString("product_name"), rs.getString("category")});
            }

            Map<String, List<String>> productCategoryMap = new HashMap<>(); 
            for (String cat : categories) {
                productCategoryMap.put(cat, new ArrayList<>());
            }

            for (String[] pc : productCategory) {
                productCategoryMap.get(pc[1]).add(pc[0]);
            }

            // Close connection
            rs.close();
            stmt.close();
            conn.close();

            return productCategoryMap;

        } catch (Exception e) {
            orderSumArea.setText("Error connecting to database:\n" + e.getMessage());
            e.printStackTrace();
            System.exit(0);

            return null;
        }
    }

    private void closeWindow() { 
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
