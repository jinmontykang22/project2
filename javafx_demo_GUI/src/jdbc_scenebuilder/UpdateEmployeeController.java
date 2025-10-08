package jdbc_scenebuilder;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class UpdateEmployeeController {

    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField roleField;
    @FXML private TextField salaryField;
    @FXML private TextField hoursField;

    private String staffId;

    public void setEmployeeData(String id, String name, String role, double salary, int hours) {
        this.staffId = id;
        idField.setText(id);
        nameField.setText(name);
        roleField.setText(role);
        salaryField.setText(String.valueOf(salary));
        hoursField.setText(String.valueOf(hours));
    }

    @FXML
    private void saveChanges() {
        try {
            String name = nameField.getText();
            String role = roleField.getText();
            double salary = Double.parseDouble(salaryField.getText());
            int hoursWorked = Integer.parseInt(hoursField.getText());

            String sql = String.format(
                    "UPDATE staff SET name='%s', role='%s', salary=%.2f, hours_worked=%d WHERE staff_id='%s';",
                    name, role, salary, hoursWorked, staffId
            );

            ManagerViewController db = new ManagerViewController();
            db.runQuery(sql);

            Stage stage = (Stage) nameField.getScene().getWindow();
            stage.close();

        } catch (NumberFormatException nfe) {
            new Alert(Alert.AlertType.WARNING, "Salary and Hours must be numeric.").showAndWait();
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