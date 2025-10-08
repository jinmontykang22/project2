package jdbc_scenebuilder;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AddInventoryController {

    @FXML private TextField nameField;
    @FXML private TextField unitsField;
    @FXML private TextField servingsField;

    @FXML
    private void saveInventory() {
        try {
            String name = nameField.getText();
            int units = Integer.parseInt(unitsField.getText());
            int servings = Integer.parseInt(servingsField.getText());

            String sql = String.format(
                    "INSERT INTO inventory (name, units_remaining, numServings) VALUES ('%s', %d, %d);",
                    name, units, servings
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