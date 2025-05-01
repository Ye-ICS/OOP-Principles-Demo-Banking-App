import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

public class MainMenuController {
    
    @FXML
    private BorderPane rootContainer;

    private AccountManager accountManager;


    MainMenuController(AccountManager accountManager) {
        this.accountManager = accountManager;
    }


    @FXML
    private void exit(ActionEvent event) {
        System.out.println("Bye.");
        System.exit(0);
    }

    @FXML
    private void openUserMenu(ActionEvent event) throws IOException {
        // TODO: Pass the account manager to the user menu controller.
        Parent userMenuLayout = (Parent) FXMLLoader.load(getClass().getResource("UserMenuLayout.fxml"));
        rootContainer.getScene().setRoot(userMenuLayout);
    }

    @FXML
    private void openUserNameSearch(ActionEvent event) throws IOException {
        // TODO: Pass the account manager to the user name search controller.
        Parent userMenuLayout = (Parent) FXMLLoader.load(getClass().getResource("UserNameSearchLayout.fxml"));
        rootContainer.getScene().setRoot(userMenuLayout);
    }
}