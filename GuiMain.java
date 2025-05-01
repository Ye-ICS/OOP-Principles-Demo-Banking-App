import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GuiMain extends Application {
    private static final String ACCOUNTS_FILE = "accounts.txt";
    private static final String USERS_FILE = "users.txt";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        AccountManager accountMan = new AccountManager(ACCOUNTS_FILE, USERS_FILE);

        FXMLLoader mainMenuloader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        Parent root = mainMenuloader.load();
        
        MainMenuController mainMenuController = new MainMenuController(accountMan);
        mainMenuloader.setController(mainMenuController);

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Banking App 3000");
        stage.show();
    }
}
