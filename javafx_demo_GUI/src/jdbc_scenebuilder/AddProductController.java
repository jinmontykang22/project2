package jdbc_scenebuilder;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AddProductController {

    @FXML private TextField nameField;
    @FXML private TextField priceField;
    @FXML private TextField categoryField;
    @FXML private TextField flavorField;
    @FXML private TextField milkField;
    @FXML private TextField creamField;
    @FXML private TextField sugarField;

    @FXML
    private void saveProduct() {
        try {
            String name = nameField.getText();
            double price = Double.parseDouble(priceField.getText());
            String category = categoryField.getText();
            int flavor = Integer.parseInt(flavorField.getText());
            double milk = Double.parseDouble(milkField.getText());
            int cream = Integer.parseInt(creamField.getText());
            int sugar = Integer.parseInt(sugarField.getText());

            String sql = String.format(
                    "INSERT INTO products (product_name, price, category, flavor, milk, cream, sugar) " +
                            "VALUES ('%s', %.2f, '%s', %d, %.2f, %d, %d);",
                    name, price, category, flavor, milk, cream, sugar
            );

            ManagerViewController db = new ManagerViewController();
            db.runQuery(sql);

            // Close the window after saving
            Stage stage = (Stage) nameField.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).showAndWait();
        }
    }

    @FXML
    private void cancel() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }
}