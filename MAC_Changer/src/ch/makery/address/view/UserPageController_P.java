package ch.makery.address.view;

import java.io.IOException;
import java.net.URL;

import ch.makery.address.MainApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Paint;

public class UserPageController_P {
    
    @FXML
    private AnchorPane APAttribMac;
    @FXML
    private Label lAttribMac;
    @FXML
    private Label lNouvelleMac;
    @FXML
    private BorderPane BPAttribMac;

    private MainApp mainApp;
    
    @FXML
    private void initialize() {
    	handleAttribMac();
    }
    
    public UserPageController_P() throws IOException {
    }
    
    private FXMLLoader changerAnchor(String fichierFXML) {
    	try {
            URL url = getClass().getResource(fichierFXML);
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(url);
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
            AnchorPane page = (AnchorPane) fxmlLoader.load(url.openStream()); 
            BPAttribMac.setCenter(page);
            return fxmlLoader;
        } 
        catch (IOException e) {
        	System.out.println("erreur");
            e.printStackTrace();
        }
    	return null;
    }
    
    @FXML	
    private void handleDisconnect() {
    	mainApp.showConnexionOverview();
  	}
    
    @FXML	
    private void handleAttribMac() {
    	lNouvelleMac.setBackground(lAttribMac.getBackground());
    	lAttribMac.setBackground(new Background(new BackgroundFill(Paint.valueOf("lightGray"), CornerRadii.EMPTY, Insets.EMPTY)));
    	changerAnchor("AttribMac_P.fxml");
    }
    
    @FXML	
    private void handleProposerNouvelleAdr() {
    	lAttribMac.setBackground(lNouvelleMac.getBackground());
    	lNouvelleMac.setBackground(new Background(new BackgroundFill(Paint.valueOf("lightGray"), CornerRadii.EMPTY, Insets.EMPTY)));
    	FXMLLoader loader = changerAnchor("ProposeMac_P.fxml");
    	ProposeNvlleAdrMacController controller = loader.getController();
    	controller.setMainApp(mainApp);    	
  	}
    
    @FXML
    private void afficherMac() {
    	
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}



