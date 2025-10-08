package jdbc_scenebuilder;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class UpdateProductController {

    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField priceField;
    @FXML private TextField categoryField;
    @FXML private TextField flavorField;
    @FXML private TextField milkField;
    @FXML private TextField creamField;
    @FXML private TextField sugarField;

    private int productId;

    /** Called by the main controller to pre-fill fields before showing the dialog */
    public void setProductData(int id, String name, double price, String category, int flavor, double milk, int cream, int sugar) {
        productId = id;
        idField.setText(String.valueOf(id));
        nameField.setText(name);
        priceField.setText(String.valueOf(price));
        categoryField.setText(category);
        flavorField.setText(String.valueOf(flavor));
        milkField.setText(String.valueOf(milk));
        creamField.setText(String.valueOf(cream));
        sugarField.setText(String.valueOf(sugar));
    }

    @FXML
    private void updateProduct() {
        try {
            String name = nameField.getText();
            double price = Double.parseDouble(priceField.getText());
            String category = categoryField.getText();
            int flavor = Integer.parseInt(flavorField.getText());
            double milk = Double.parseDouble(milkField.getText());
            int cream = Integer.parseInt(creamField.getText());
            int sugar = Integer.parseInt(sugarField.getText());

            String sql = String.format(
                    "UPDATE products SET product_name='%s', price=%.2f, category='%s', flavor=%d, milk=%.2f, cream=%d, sugar=%d WHERE product_id=%d;",
                    name, price, category, flavor, milk, cream, sugar, productId
            );

            ManagerViewController db = new ManagerViewController();
            db.runQuery(sql);

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