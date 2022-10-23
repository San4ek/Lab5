import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Lab5Controller implements Initializable {
    @FXML
    public TableView<User> UserTable;
    @FXML
    public TableColumn<User, Integer> idColumn;
    @FXML
    public TableColumn<User, String> nameColumn;
    @FXML
    public TableColumn<User, String> surnameColumn;
    @FXML
    public TableColumn<User, String> patronymicColumn;
    @FXML
    public TextField nameField;
    @FXML
    public TextField surnameField;
    @FXML
    public TextField patronymicField;
    @FXML
    public Button addButton;

    DatabaseHandler dbHandler = new DatabaseHandler();

    private final ObservableList<User> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addButton.setOnAction(actionEvent -> {
            User user =new User(nameField.getText(), surnameField.getText(), patronymicField.getText());
            data.add(user);
            dbHandler.insertUser(user);
        });

        addInfAboutUser();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        patronymicColumn.setCellValueFactory(new PropertyValueFactory<>("patronymic"));
        UserTable.setItems(data);
    }

    private void addInfAboutUser() {
        ResultSet users = dbHandler.getUsers();
        try {
            while (users.next()) {
                User user = new User(users.getInt(1),
                        users.getString(2),
                        users.getString(3),
                        users.getString(4));

                data.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
