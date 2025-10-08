package jdbc_scenebuilder;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import java.sql.*;
import java.util.*;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ManagerViewController {

    @FXML
    private Button switch_view; //match the fx:id value from Scene Builder

    @FXML
    private TableView<ObservableList<String>> tableArea; //match the fx:id value from Scene Builder

    @FXML
    private BarChart<String, Number> barChart;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private LineChart<String, Number> lineChart;
    @FXML
    private CategoryAxis lineXAxis;
    @FXML
    private NumberAxis lineYAxis;


    private static final String DB_URL = "jdbc:postgresql://csce-315-db.engr.tamu.edu/gang_52_db"; //database location

    // Set axis labels
    private String numericColumn, categoryColumn, yAxisName, xAxisName;

    @FXML
    private void switchToCashierView(ActionEvent event) throws Exception {
        main.changeScene("./resources/cashier-view.fxml");
    }

    // This method runs automatically when the FXML loads
    @FXML
    public void initialize() {
        // Set up what happens when button is clicked
        // switch_view.setOnAction(event -> runQuery("SELECT product_name FROM products"));
        // This needs to switch back to the login page.
        setMenu();

    }
    //Define an action whenever a new view is selected from the dropdown menu.
    @FXML
    public void setMenu() {
        String csvResult = runQuery("SELECT * FROM products LIMIT 100");
        List<Map<String, String>> data = parseCsvToMap(csvResult);
        populateTableView(csvResult);
        numericColumn = "price";
        categoryColumn = "category";
        yAxisName = "Average Price";
        xAxisName = "Category";
        populateBarChart(data);
    }

    @FXML
    public void setOrders() {
        String csvResult = runQuery("SELECT * FROM orders");
        List<Map<String, String>> data = parseCsvToMap(csvResult);
        populateTableView(runQuery("SELECT * FROM orders LIMIT 100"));
        numericColumn = "total_price";
        categoryColumn = "month";
        yAxisName = "Average Price/Order";
        xAxisName = "Months";
        populateBarChart(data);

        // Take the last 1000 orders and display them on the line chart.
//        csvResult = runQuery("SELECT * FROM orders ORDER BY order_time DESC LIMIT 1000");
//        data = parseCsvToMap(csvResult);
//        numericColumn = "order_id";
//        categoryColumn = "day";
//        yAxisName = "Number of Orders";
//        xAxisName = "Days";
//        populateLineChart(data);
    }
    @FXML
    public void setStaff() {
        String csvResult = runQuery("SELECT * FROM staff");
        List<Map<String, String>> data = parseCsvToMap(csvResult);
        populateTableView(csvResult);
        numericColumn = "hours_worked";
        categoryColumn = "role";
        yAxisName = "Average Hours Worked";
        xAxisName = "Role";
        populateBarChart(data);
    }

    @FXML
    public void setIngredients() {
        barChart.getData().clear();
        populateTableView(runQuery("SELECT * FROM ingredients LIMIT 100"));
    }

    @FXML
    public void setItems() {
        // Return the last 1000 items added to the order table.
        String csvResult = runQuery("SELECT * FROM items ORDER BY item_id DESC LIMIT 1000");
        List<Map<String, String>> data = parseCsvToMap(csvResult);
        populateTableView(csvResult);
        numericColumn = "Units_Remaining";
        categoryColumn = "Name";
        yAxisName = "Stock";
        xAxisName = "Item";
        populateBarChart(data);
    }

    @FXML
    public void setInventory() {
        String csvResult = runQuery("SELECT * FROM inventory");
        List<Map<String, String>> data = parseCsvToMap(csvResult);
        populateTableView(csvResult);
        numericColumn = "units_remaining";
        categoryColumn = "name";
        yAxisName = "Units Remaining";
        xAxisName = "Item Name";
        populateBarChart(data);
    }

    private List<Map<String, String>> parseCsvToMap(String csvData) {
        List<Map<String, String>> data = new ArrayList<>();
        String[] lines = csvData.split("\n");
        if (lines.length < 1) {
            return data;
        }

        String[] headers = lines[0].split(",");
        for (int i = 1; i < lines.length; i++) {
            String[] rowData = lines[i].split(",", -1); // -1 to handle empty values
            if (rowData.length == headers.length) {
                Map<String, String> rowMap = new HashMap<>();
                for (int j = 0; j < headers.length; j++) {
                    rowMap.put(headers[j], rowData[j]);
                }
                data.add(rowMap);
            }
        }
        return data;
    }

    @FXML
    private void addProduct() {
        try {
            setMenu();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("./resources/add-product.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Add Product");
            stage.setScene(new Scene(loader.load()));
            stage.showAndWait();  // wait until user closes the dialog
            setItems();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void updateProduct() {
        try {
            // Get selected row from table
            ObservableList<String> selectedRow = tableArea.getSelectionModel().getSelectedItem();
            if (selectedRow == null) {
                new Alert(Alert.AlertType.WARNING, "Please select a product to update.").showAndWait();
                return;
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("./resources/update-product.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Update Product");
            stage.setScene(new Scene(loader.load()));

            // Pass selected data to controller
            UpdateProductController controller = loader.getController();
            controller.setProductData(
                    Integer.parseInt(selectedRow.get(0)), // product_id
                    selectedRow.get(1),                   // product_name
                    Double.parseDouble(selectedRow.get(2)), // price
                    selectedRow.get(3),                   // category
                    Integer.parseInt(selectedRow.get(4)), // flavor
                    Double.parseDouble(selectedRow.get(7)), // milk
                    Integer.parseInt(selectedRow.get(8)), // cream
                    Integer.parseInt(selectedRow.get(9))  // sugar
            );

            stage.showAndWait();
            setItems();

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).showAndWait();
        }
    }

    @FXML
    private void addInventory() {
        try {
            setInventory();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("./resources/add-inventory.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Add Inventory");
            stage.setScene(new Scene(loader.load()));
            stage.showAndWait(); // Wait for user to finish
            setInventory();        // Refresh main table
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).showAndWait();
        }
    }

    @FXML
    private void updateInventory() {
        try {
            // Get selected row from your inventory table (assuming it's called inventoryArea)
            ObservableList<String> selectedRow = tableArea.getSelectionModel().getSelectedItem();
            if (selectedRow == null) {
                new Alert(Alert.AlertType.WARNING, "Please select an inventory item to update.").showAndWait();
                return;
            }

            // Load the update-inventory.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("./resources/update-inventory.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Update Inventory");
            stage.setScene(new Scene(loader.load()));

            // Pass selected row data to controller
            UpdateInventoryController controller = loader.getController();
            controller.setInventoryData(
                    Integer.parseInt(selectedRow.get(0)),  // inv_item_id
                    selectedRow.get(1),                    // name
                    Integer.parseInt(selectedRow.get(2)),  // units_remaining
                    Integer.parseInt(selectedRow.get(3))   // numServings
            );

            stage.showAndWait();
            setInventory();

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).showAndWait();
        }
    }

    @FXML
    private void addEmployee() {
        try {
            setStaff();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("./resources/add-employee.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Add Employee");
            stage.setScene(new Scene(loader.load()));
            stage.showAndWait();   // Wait until the dialog closes
            setStaff();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).showAndWait();
        }
    }

    @FXML
    private void updateEmployee() {
        try {
            // Get selected row from your staff table
            ObservableList<String> selectedRow = tableArea.getSelectionModel().getSelectedItem();
            if (selectedRow == null) {
                new Alert(Alert.AlertType.WARNING, "Please select an employee to update.").showAndWait();
                return;
            }

            // Load FXML for the update window
            FXMLLoader loader = new FXMLLoader(getClass().getResource("./resources/update-employee.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Update Employee");
            stage.setScene(new Scene(loader.load()));

            // Pass selected employee data to the controller
            UpdateEmployeeController controller = loader.getController();
            controller.setEmployeeData(
                    selectedRow.get(0),                      // staff_id
                    selectedRow.get(1),                      // name
                    selectedRow.get(2),                      // role
                    Double.parseDouble(selectedRow.get(3)),  // salary
                    Integer.parseInt(selectedRow.get(4))     // hours_worked
            );

            stage.showAndWait();
            setStaff();

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).showAndWait();
        }
    }

    @FXML
    private void removeEmployee() {
        try {
            // Get selected row from the staff table
            ObservableList<String> selectedRow = tableArea.getSelectionModel().getSelectedItem();
            if (selectedRow == null) {
                new Alert(Alert.AlertType.WARNING, "Please select an employee to remove.").showAndWait();
                return;
            }

            String staffId = selectedRow.get(0);
            String staffName = selectedRow.get(1);

            // Confirm deletion
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirm Delete");
            confirm.setHeaderText("Delete Employee");
            confirm.setContentText("Are you sure you want to remove " + staffName + " (ID: " + staffId + ")?");

            // Wait for user response
            if (confirm.showAndWait().get() == ButtonType.OK) {
                String sql = String.format("DELETE FROM staff WHERE staff_id = '%s';", staffId);

                ManagerViewController db = new ManagerViewController();
                db.runQuery(sql);

                new Alert(Alert.AlertType.INFORMATION, "Employee removed successfully.").showAndWait();
                setStaff();
            }

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error removing employee: " + e.getMessage()).showAndWait();
        }
    }

    private void populateLineChart(List<Map<String, String>> data) {
        lineChart.getData().clear();
        if (data.isEmpty()) {
            return;
        }

        xAxis.setLabel(xAxisName);
        yAxis.setLabel(yAxisName);

        // Populate chart
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(numericColumn);
        for (Map<String, String> row : data) {
            String category = row.get(categoryColumn);
            String valueStr = row.get(numericColumn);
            try {
                double value = Double.parseDouble(valueStr);
                series.getData().add(new XYChart.Data<>(category, value));
            } catch (NumberFormatException e) {
                System.err.println("Invalid number format for " + valueStr);
            }
        }
        lineChart.getData().add(series);
    }

    private void populateBarChart(List<Map<String, String>> data) {
        barChart.getData().clear();
        if (data.isEmpty()) {
            return;
        }

        xAxis.setLabel(xAxisName);
        yAxis.setLabel(yAxisName);

        // Populate chart
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(numericColumn);
        for (Map<String, String> row : data) {
            String category = row.get(categoryColumn);
            String valueStr = row.get(numericColumn);
            if (valueStr == null || valueStr.isEmpty() || valueStr.equalsIgnoreCase("none")) {
                continue;
            }
            try {
                double value = Double.parseDouble(valueStr);
                series.getData().add(new XYChart.Data<>(category, value));
            } catch (NumberFormatException e) {
                System.err.println("Invalid number format for value: " + valueStr);
            }
        }
        barChart.getData().add(series);
    }

    //method to select a drink and add it to the Order Summary display
    public void populateTableView(String qryResult){
        // Ensures the values of the table are cleared, including the column header structure.
        tableArea.getColumns().clear();
        tableArea.getItems().clear();

        String[] lines = qryResult.split("\n");
        if (lines.length < 1) {
            return; // No data
        }

        // Get headers from first line
        String[] headers = lines[0].split(",");

        // Create columns dynamically
        for (int i = 0; i < headers.length; i++) {
            final int colIndex = i;
            TableColumn<ObservableList<String>, String> column = new TableColumn<>(headers[i]);

            column.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().get(colIndex)));

            tableArea.getColumns().add(column);
        }

        // Populate rows (skip header row)
        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
        for (int i = 1; i < lines.length; i++) {
            String[] rowData = lines[i].split(",");
            ObservableList<String> row = FXCollections.observableArrayList(rowData);
            data.add(row);
        }
        tableArea.setItems(data);
    }


    // Your method to run the database query
    public String runQuery(String sqlStatement) {
        dbSetup my = new dbSetup();
        StringBuilder result = new StringBuilder();

        try {
            Class.forName("org.postgresql.Driver");
            try (Connection conn = DriverManager.getConnection(DB_URL, my.user, my.pswd);
                 Statement stmt = conn.createStatement()) {

                // Detect query type
                if (sqlStatement.trim().toLowerCase().startsWith("select")) {
                    try (ResultSet rs = stmt.executeQuery(sqlStatement)) {
                        ResultSetMetaData metaData = rs.getMetaData();
                        int columnCount = metaData.getColumnCount();

                        // Append headers
                        for (int i = 1; i <= columnCount; i++) {
                            result.append(metaData.getColumnName(i));
                            if (i < columnCount) result.append(",");
                        }
                        result.append("\n");

                        // Append rows
                        while (rs.next()) {
                            for (int i = 1; i <= columnCount; i++) {
                                String value = rs.getString(i);
                                result.append(value != null ? value : "none");
                                if (i < columnCount) result.append(",");
                            }
                            result.append("\n");
                        }
                    }
                } else {
                    int affectedRows = stmt.executeUpdate(sqlStatement);
                    result.append("Rows affected: ").append(affectedRows);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return "Error: PostgreSQL driver not found";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error: SQL Exception - " + e.getMessage();
        }

        return result.toString();
    }
}

