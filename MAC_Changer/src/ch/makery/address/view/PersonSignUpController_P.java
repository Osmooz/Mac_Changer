package ch.makery.address.view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ch.makery.address.MainApp;
import ch.makery.address.model.User_P;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class PersonSignUpController_P {

	@FXML
	private  TextField pseudoField;
	
	@FXML
	private TextField emailField;
	
	
	@FXML
	private PasswordField mdpField;
	
	@FXML
	private PasswordField confirmationMdpField;
	
	private Stage dialogStage;
	private boolean registerClicked = false;
	private MainApp mainApp;
	
	public PersonSignUpController_P() {
		
	}
	
	@FXML
	private void initialize() {
		
	}
	
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	public boolean isRegisterClicked() {
		return registerClicked;
	}
	
	private boolean areInputsValid() {
        String errorMessage = "";

        if (pseudoField.getText() == null || pseudoField.getText().length() == 0 || pseudoField.getText().length() > 32) {
            errorMessage += "Le pseudo ne respecte pas les règles. Il doit avoir une taille allant de 1 à 32 caractères!\n";
        }
        Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
        Matcher m = p.matcher(emailField.getText());
        if (!(m.find() && m.group().equals(emailField.getText()))) {
        	errorMessage += "Email non valide!\n";
        }
        if (mdpField.getText() == null || mdpField.getText().length() < 3 || mdpField.getText().length() > 20) {
            errorMessage += "Mot de passe non valide! Le mot de passe doit faire entre 3 et 20 caractères\n";
        }
        System.out.println("mdp : " + mdpField.getText());
        if (!confirmationMdpField.getText().equals(mdpField.getText())) {
            errorMessage += "Mots de passe différents!\n" + confirmationMdpField.getText() + " - " + mdpField.getText();
        } 

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Champs invalides");
            alert.setHeaderText("Veuillez corriger les champs suivants qui sont invalides :");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
	
	@FXML
	private void handleRegister() {
		if(areInputsValid()) {
			System.out.println("On ajoute le user : " + pseudoField.getText() + " - " + emailField.getText() + " - " + mdpField.getText());
			//TODO: Ajouter l'utilisateur dans la BD grace aux champs ci dessus. Penser à faire la validation par email
			mainApp.getUserData().add(new User_P(pseudoField.getText(), emailField.getText(), mdpField.getText()));
			registerClicked = true;
            dialogStage.close();
		}
	}
	
	@FXML
    private void handleCancel() {
        dialogStage.close();
    }
	
	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
	
}
