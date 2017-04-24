	package ch.makery.address.view;

import java.net.URI;
import ch.makery.address.MainApp;
import ch.makery.address.util.DesktopApi;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class ConnexionController_P {

    @FXML
    private TextField pseudoLabel;
    @FXML
    private PasswordField mdpLabel;
 
    private MainApp mainApp;

    public ConnexionController_P() {
    }

    @FXML
    private void initialize() {

    }

    @FXML
    private void handleSignIn() {
    	boolean isUser = false;
    	for(int i = 0 ; i < mainApp.getUserData().size() ; i++) { //TODO: supprimer la boucle for après avoir mis les requettes de la BD
    		if(mainApp.getUserData().get(i).getPseudo().getValue().equals(pseudoLabel.getText())) {
    			if(mainApp.getUserData().get(i).getMdp().getValue().equals(mdpLabel.getText())) {
    				isUser = true;
    				mainApp.showUserDesk(mainApp.getUserData().get(i)); //TODO: Changer param afin de savoir si c'est un admin ou non
    			}
    		}
    	}
    	
    	
    	//TODO: faire la vérification avec la base de donnée ici.
//    	if(/****PSEUDO****/.equals(pseudoLabel.getText())) { 
//			if(/****MOT DE PASSE DE CE PSEUDO****/.getValue().equals(mdpLabel.getText())) {
//				isUser = true;
//				mainApp.showUserDesk(mainApp.getUserData().get(i)); //TODO: Changer param afin de savoir si c'est un admin ou non
//			}
//		}
    	
    	
    	if(!isUser) {//Si un des champs ne contient pas les bonnes info
    		Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Erreur");
            alert.setHeaderText("Utilisateur inconnu");
            alert.setContentText("Mauvais pseudo ou mot de passe");

            alert.showAndWait();
        }
    }
    
    @FXML
    public void onEnter(ActionEvent ae){
       handleSignIn();
    }
    
    @FXML
    private void handleSignUp() {
    	mainApp.showSignUpDialog();
    }
    
    @FXML
    private void handleMdpOublie() {
    	URI uri = URI.create("http://projet-info-l3.alwaysdata.net/");
    	DesktopApi.browse(uri);
    }
    
    @FXML
    private void handleInvite() {
    	mainApp.showUserDesk(null);  
    }
    
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    
}
