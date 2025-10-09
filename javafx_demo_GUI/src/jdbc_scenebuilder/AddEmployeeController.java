package jdbc_scenebuilder;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AddEmployeeController {

    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField roleField;
    @FXML private TextField salaryField;
    @FXML private TextField hoursField;

    @FXML
    private void saveEmployee() {
        try {
            String staffId = idField.getText().trim();
            String name = nameField.getText().trim();
            String role = roleField.getText().trim();
            double salary = Double.parseDouble(salaryField.getText().trim());
            int hoursWorked = Integer.parseInt(hoursField.getText().trim());

            String sql = String.format(
                    "INSERT INTO staff (staff_id, name, role, salary, hours_worked) " +
                            "VALUES ('%s', '%s', '%s', %.2f, %d);",
                    staffId, name, role, salary, hoursWorked
            );

            ManagerViewController db = new ManagerViewController();
            db.runQuery(sql);

            // close window
            Stage stage = (Stage) nameField.getScene().getWindow();
            stage.close();

        } catch (NumberFormatException nfe) {
            new Alert(Alert.AlertType.WARNING, "Salary and Hours Worked must be numeric.").showAndWait();
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