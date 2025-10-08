package jdbc_scenebuilder;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Button categoryBtn1;

    @FXML
    private Button categoryBtn2;

    @FXML
    private Button categoryBtn3;

    @FXML
    private Button sizeBtn1;

    @FXML
    private Button sizeBtn2;

    @FXML
    private Button sizeBtn3;

    @FXML
    private Button sizeBtn4;

    @FXML
    private Button sugarBtn1;
    @FXML
    private Button sugarBtn2;
    @FXML
    private Button sugarBtn3;
    @FXML
    private Button sugarBtn4;

    @FXML
    private Button iceBtn1;
    @FXML
    private Button iceBtn2;
    @FXML
    private Button iceBtn3;
    @FXML
    private Button iceBtn4;

    @FXML
    private Button toppingBtn1;
    @FXML
    private Button toppingBtn2;
    @FXML
    private Button toppingBtn3;
    @FXML
    private Button toppingBtn4;

    @FXML
    private Button itemConfirmBtn1;

    @FXML
    private Button orderConfirmBtn1;

    @FXML
    private DialogPane customizationPopUp1;

    @FXML
    private TextArea orderSumArea; //match the fx:id value from Scene Builder

    @FXML
    private void switchToManagerView(ActionEvent event) throws Exception {
        main.changeScene("./resources/manager-view.fxml");
    }


    private static final String DB_URL = "jdbc:postgresql://csce-315-db.engr.tamu.edu/gang_52_db"; //database location

    private Button[] drinkButtons;
    private Button[] categoryButtons;
    private Button[] sizeButtons;
    private Button[] sugarButtons;
    private Button[] iceButtons;
    private Button[] toppingButtons;
    private String[] categories;

    private String productName;
    private int productID;
    private String itemDrink;
    private String itemSize;
    private String itemSugar;
    private String itemIce;
    private double itemPrice;
    private List<String> itemToppings;

    private int orderID = 1;
    private int itemID = 1;
    private List<Item> currentOrderItems;

    // This method runs automatically when the FXML loads
    @FXML
    public void initialize() {
        //ordering of these initialization statements not very clean but it works for now
        drinkButtons = new Button[]{drinkBtn1, drinkBtn2, drinkBtn3, drinkBtn4, drinkBtn5, drinkBtn6,
                drinkBtn7, drinkBtn8, drinkBtn9, drinkBtn10, drinkBtn11, drinkBtn12};

        sizeButtons = new Button[]{sizeBtn1, sizeBtn2, sizeBtn3, sizeBtn4};
        sugarButtons = new Button[]{sugarBtn1, sugarBtn2, sugarBtn3, sugarBtn4};
        iceButtons = new Button[]{iceBtn1, iceBtn2, iceBtn3, iceBtn4};
        toppingButtons = new Button[]{toppingBtn1, toppingBtn2, toppingBtn3, toppingBtn4};

        categoryButtons = new Button[]{categoryBtn1, categoryBtn2, categoryBtn3};

        categories = new String[]{"hot", "milk", "fruit"};
        itemToppings = new ArrayList<>();
        currentOrderItems = new ArrayList<>();

        closeButton.setOnAction(event -> closeWindow());

        Map<String, List<String>> prod_cat_map = create_product_category_map("SELECT product_name, category FROM products");

        for (Button b : drinkButtons) {
            b.setText("");
            b.setVisible(false);
            b.setOnAction(event -> selectDrinkBtn(b.getText()));
        }

        for (int i = 0; i < categoryButtons.length; i++) {
            int lambda_i = i; //needs this for some reason: local variables referenced from a lambda expression must be final or effectively final
            categoryButtons[i].setText(categories[i].toUpperCase());
            categoryButtons[i].setOnAction(event -> populateByCategory(categories[lambda_i], prod_cat_map));
        }

        for (Button b : sizeButtons) {
            b.setOnAction(event -> selectSizeBtn(b.getText()));
        }

        for (Button b : sugarButtons) {
            b.setOnAction(event -> selectSugarBtn(b.getText()));
        }

        for (Button b : iceButtons) {
            b.setOnAction(event -> selectIceBtn(b.getText()));
        }

        for (Button b : toppingButtons) {
            b.setOnAction(event -> selectToppingBtn(b.getText()));
        }

        itemConfirmBtn1.setOnAction(event -> completeItemBtn());
        orderConfirmBtn1.setOnAction(event -> completeOrderBtn());

        populateByCategory(categories[0], prod_cat_map);
        customizationPopUp1.setVisible(false);
    }

    private void selectSizeBtn(String sizeBtnStr) {
        itemSize = sizeBtnStr;
    }

    private void selectSugarBtn(String sugarBtnStr) {
        itemSugar = sugarBtnStr;
    }

    private void selectIceBtn(String iceBtnStr) {
        itemIce = iceBtnStr;
    }

    private void selectToppingBtn(String toppingBtnStr) {
        if (toppingBtnStr.equals("None")) {
            itemToppings.clear();
        }
        else {
            itemToppings.add(toppingBtnStr);
        }
    }

    private void runUpdate(String sqlStatement) {
        try {
            // Get database creditials
            dbSetup my = new dbSetup();

            // Build the connection
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, my.user, my.pswd);

            // Create statement
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sqlStatement);

            // Close connection
            stmt.close();
            conn.close();
        } catch (Exception e) {
            orderSumArea.setText("Error writing to database:\n" + e.getMessage());
            e.printStackTrace();
            System.exit(0);
        }
    }
    private void completeOrderBtn() {
        double orderTotal = 0.0;
        for (Item i : currentOrderItems) {
            orderTotal += i.getTotalPrice();
            String sizeEscaped = i.getSize().replace("'", "''"); //solely due to the "Bucee's" size (can we not use quotes in sql entries please)
            String sqlStatement = "INSERT INTO test_items (item_id, order_id, product_id, quantity, size, sugar_level, ice_level, toppings, price) VALUES (" +
                    i.getItemID() + ", " + i.getOrderID() + ", " + i.getProductID() + ", 1, '" + sizeEscaped + "', '" +
                    i.getSugarLevel() + "', '" + i.getIceLevel() + "', '" + i.getToppings() + "', " + i.getTotalPrice() + ");";
            runUpdate(sqlStatement);
        }
        Order newOrder = new Order(orderID, orderTotal, 0.0, ""); 
        //inserts into a dummy table for now
        String sqlStatement = "INSERT INTO test_orders (order_id, order_time, month, total_price, tip, special_notes) VALUES (" +
                newOrder.getOrderID() + ", '" + newOrder.getOrderTime() + "', " + newOrder.getMonth() + ", " +
                newOrder.getTotalPrice() + ", " + newOrder.getTip() + ", '" + newOrder.getSpecialNotes() + "');";

        runUpdate(sqlStatement);

        orderID += 1;
        itemID = 1;
        orderSumArea.setText("");
        currentOrderItems.clear();
    }

    private void completeItemBtn() {
        productID = Integer.parseInt(runQuery("SELECT product_id FROM products WHERE product_name = '" + productName + "'", "product_id").replace(",", ""));

        if (itemSize.equals("Small")) {
            itemPrice -= 0.5;
        } else if (itemSize.equals("Large")) {
            itemPrice += 0.5;
        } else if (itemSize.equals("Bucee's")) {
            itemPrice += 1.0;
        }

        if (itemToppings.contains("Boba")) {
            itemPrice += 0.5;
        }
        if (itemToppings.contains("Jelly")) {
            itemPrice += 0.5;
        }
        if (itemToppings.contains("Pudding")) {
            itemPrice += 0.5;
        }
        
        Item newItem = new Item(productName, itemID, orderID, productID, itemPrice, itemSize, itemSugar, itemIce, String.join(",", itemToppings));
        currentOrderItems.add(newItem);

        String currentText = orderSumArea.getText();
        orderSumArea.setText(currentText + newItem + "\n");

        itemID += 1;
        itemSize = "";
        itemSugar = "";
        itemIce = "";

        itemToppings.clear();
        customizationPopUp1.setVisible(false);
    }
    //on button click, "opens" the customization pop-up and sets the product name and price
    private void selectDrinkBtn(String drinkBtnStr) {
        productName = drinkBtnStr;
        String dp = runQuery("SELECT price FROM products WHERE product_name = '" + productName + "'", "price");
        itemPrice = Double.parseDouble(dp.replace(",", ""));
        customizationPopUp1.setVisible(true);
    }

    //populates the drink buttons based on the selected category
    private void populateByCategory(String category, Map<String, List<String>> product_category_map) {
        List<String> product_names = product_category_map.get(category);

        for (Button b : drinkButtons) {
            b.setText("");
            b.setVisible(false);
        }

        for (int i = 0; i < product_names.size() && i < drinkButtons.length; i++) {
            drinkButtons[i].setText(product_names.get(i));
            drinkButtons[i].setVisible(true);
        }
    }

    // Your method to run the database query 
    private String runQuery(String sqlStatement, String columnLabel) {
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

            return result;

        } catch (Exception e) {
            orderSumArea.setText("Error connecting to database:\n" + e.getMessage());
            e.printStackTrace();
            System.exit(0);

            return "ERROR";
        }
    }

    // Creates a map of product names to their categories. used in various places
    private Map<String, List<String>> create_product_category_map(String sqlStatement) {
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

            List<String[]> product_category = new ArrayList<>(); //no tuple in java ;-;
            while (rs.next()) {
                product_category.add(new String[]{rs.getString("product_name"), rs.getString("category")});
            }

            Map<String, List<String>> product_category_map = new HashMap<>();
            for (String cat : categories) {
                product_category_map.put(cat, new ArrayList<>());
            }

            for (String[] pc : product_category) {
                String category = pc[1];
                String productName = pc[0];
                product_category_map
                    .computeIfAbsent(category, k -> new ArrayList<>())
                    .add(productName);
            }

            // Close connection
            rs.close();
            stmt.close();
            conn.close();

            return product_category_map;

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
