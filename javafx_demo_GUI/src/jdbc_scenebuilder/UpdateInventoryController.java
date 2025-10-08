package jdbc_scenebuilder;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class UpdateInventoryController {

    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField unitsField;
    @FXML private TextField servingsField;

    private int inv_item_id;

    public void setInventoryData(int id, String name, int units, int servings) {
        this.inv_item_id = id;
        idField.setText(String.valueOf(id));
        nameField.setText(name);
        unitsField.setText(String.valueOf(units));
        servingsField.setText(String.valueOf(servings));
    }

    @FXML
    private void saveChanges() {
        try {
            String name = nameField.getText();
            int units = Integer.parseInt(unitsField.getText());
            int servings = Integer.parseInt(servingsField.getText());

            String sql = String.format(
                    "UPDATE inventory SET name='%s', units_remaining=%d, numServings=%d WHERE inv_item_id=%d;",
                    name, units, servings, inv_item_id
            );

            ManagerViewController db = new ManagerViewController();
            db.runQuery(sql);

            Stage stage = (Stage) nameField.getScene().getWindow();
            stage.close();

        } catch (NumberFormatException nfe) {
            new Alert(Alert.AlertType.WARNING, "Units and Servings must be numbers.").showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).showAndWait();
        }
    }

    @FXML
    private void cancel() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }
}