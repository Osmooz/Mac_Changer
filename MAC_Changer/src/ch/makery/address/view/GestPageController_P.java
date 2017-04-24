package ch.makery.address.view;

import java.io.IOException;
import java.net.URL;
import ch.makery.address.MainApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;

public class GestPageController_P {
	@FXML
    private Button connexion;
    @FXML
    private Button inscription;
    @FXML
    private AnchorPane APAttribMac;
    @FXML
    private BorderPane BPAttribMac;
 
    private MainApp mainApp;
    
    @FXML
    private void initialize() {
    	try {    
    		URL url = getClass().getResource("AttribMac_P.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(url);
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
            AnchorPane page = (AnchorPane) fxmlLoader.load(url.openStream()); 
            BPAttribMac.setCenter(page);
            AttribMacController_P controller = fxmlLoader.getController();
	    	controller.setMainApp(mainApp);
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public GestPageController_P() throws IOException {
    }
    
    @FXML	
    private void handleConnexion() {
    	mainApp.showConnexionOverview();
  	}
    
    @FXML	
    private void handleSignUp() {
    	mainApp.showSignUpDialog();
  	}

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    
}