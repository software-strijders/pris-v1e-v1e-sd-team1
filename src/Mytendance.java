import javafx.application.Application;
import javafx.stage.Stage;

import models.data.VolatileData;
import utils.FXUtils;

public class Mytendance extends Application {

    public static void main(String[] args) {
        VolatileData.createData();

        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // Simply comment out the screens that you're not currently testing
        // Please do not remove this setup, we don't want to keep logging in for every test we run
        // FXUtils.loadStage("Add new user", "/views/CreateUser.fxml", stage);
        // User.setLoggedInUser(User.getRegisteredUsers().get(0));
        // FXUtils.loadStage("Main menu", "/views/TeacherMenu.fxml", stage);
        // FXUtils.loadStage("Add new lecture", "/views/CreateLecture.fxml", stage);
        FXUtils.loadStage("Selecteer rol", "/views/RoleSelection.fxml", stage);
        stage.show();
    }
}
