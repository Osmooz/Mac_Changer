package ch.makery.address.view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ch.makery.address.MainApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class ProposeNvlleAdrMacController {

    @FXML
    private TextField adrMac;
    @FXML
    private TextField adrIP;
    @FXML
    private TextField colonne;
    @FXML
    private TextField rangee;
    @FXML
    private TextField position;
    @FXML
    private ComboBox<String> salle;
    
    public MainApp mainApp;
    
    public ProposeNvlleAdrMacController() {	
    }
    
    ObservableList<String> options;
    	    

    @FXML
    private void initialize() {
    	options = FXCollections.observableArrayList("Info1", "Info2", "Info3"); //TODO: remplacer "Info1", ... Par les noms des salles obtenus dans la BD
    	salle.setItems(options);
    }

    public static boolean isNumeric(String str)  
    {  
      try  
      {  
        @SuppressWarnings("unused")
		double d = Double.parseDouble(str);  
      }  
      catch(NumberFormatException nfe)  
      {  
        return false;  
      }  
      return true;  
    }
	
	private boolean areInputsValid() {
        String errorMessage = "";
        Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9]:[a-zA-Z0-9][a-zA-Z0-9]:[a-zA-Z0-9][a-zA-Z0-9]:[a-zA-Z0-9][a-zA-Z0-9]:[a-zA-Z0-9][a-zA-Z0-9]:[a-zA-Z0-9][a-zA-Z0-9]");
        Matcher m = p.matcher(adrMac.getText());
        if (!(m.find() && m.group().equals(adrMac.getText()))) {
            errorMessage += "L'adresse MAC ne respecte pas les règles.\n\tElle doit avoir un format xx:xx:xx:xx:xx:xx\n";
        }
        p = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
        		"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
        		"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
        		"([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
        m = p.matcher(adrIP.getText());
        if (!(m.find() && m.group().equals(adrIP.getText()))) {
        	errorMessage += "IP non valide!\n";
        }
        if (salle.getValue() == null) {
            errorMessage += "Il faut selectionner une salle.\n";
        }
        if (colonne.getText() == null || colonne.getText().length() == 0 || rangee.getText().length() > 4 || !isNumeric(colonne.getText())) {
            errorMessage += "Le numéro de colonne doit être un nombre inférieur à 9999.\n";
        }
        if (rangee.getText() == null || rangee.getText().length() == 0 || rangee.getText().length() > 4 || !isNumeric(rangee.getText())) {
            errorMessage += "Le numéro de rangée doit être un nombre inférieur à 9999.\n";
        }
        if (position.getText() == null || position.getText().length() == 0 || rangee.getText().length() > 4 || !isNumeric(position.getText())) {
            errorMessage += "Le numéro de position doit être un nombre inférieur à 9999.\n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Champs invalides");
            alert.setHeaderText("Veuillez corriger les champs suivants qui sont invalides :");
            alert.setContentText(errorMessage);

            alert.showAndWait();
            
            return false;
        }
    }
	
	@FXML
	private void handleValidate() {
		if(areInputsValid()) {
			System.out.println("Il faut ajouter les caracteristiques suivantes dans la BD : " + adrMac.getText() + " - " + adrIP.getText() + " - " + salle.getValue() + " - " + colonne.getText() + " - " + rangee.getText() + " - " + position.getText());
			//TODO: Ajouter les informations dans la BD grace aux champs ci dessus.
		}
	}
    
    
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }	
}
