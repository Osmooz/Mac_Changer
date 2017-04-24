package ch.makery.address;

import java.io.IOException;

import ch.makery.address.model.User_P;
import ch.makery.address.model.User_P.Grade;
import ch.makery.address.view.AttribMacController_P;
import ch.makery.address.view.ConnexionController_P;
import ch.makery.address.view.GestPageController_P;
import ch.makery.address.view.PersonSignUpController_P;
import ch.makery.address.view.UserPageController_P;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    public String defaultAdrMac = "";
    public String nomInterfaceEth = "";
    
    private ObservableList<User_P> userData = FXCollections.observableArrayList();
    
    public MainApp() {
        
        userData.add(new User_P("Florian", "florian.gros1996@gmail.com", "1234"));
    }
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("MAC Changer");
        
        initRootLayout();
        
        showConnexionOverview();
    }


    public void initRootLayout() {
        try {
            FXMLLoader loader =  new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void showConnexionOverview() {
    	try {
	    	FXMLLoader loader = new FXMLLoader();
	    	loader.setLocation(MainApp.class.getResource("view/FenetreConnexion_P.fxml"));
	    	AnchorPane connexionOverview = (AnchorPane) loader.load();
	    	
	    	rootLayout.setCenter(connexionOverview);
	    	
	    	ConnexionController_P controller = loader.getController();
	    	controller.setMainApp(this);
    	} catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public boolean showSignUpDialog() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/SignUp_P.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Sign Up");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            PersonSignUpController_P controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMainApp(this);

            dialogStage.showAndWait();
            return controller.isRegisterClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public void showUserDesk(User_P user) { //ouvre le panel admin, ou user, ou gest selon la demande
    	try {
    		FXMLLoader loader = new FXMLLoader();
	    	if(user == null) { //Affiche la fenetre pour les invités
	    		loader.setLocation(MainApp.class.getResource("view/FenetreInvite_P.fxml"));
	    		AnchorPane page = (AnchorPane) loader.load();
	    		GestPageController_P controller = loader.getController();
		    	controller.setMainApp(this);
	    		rootLayout.setCenter(page);
	    	}
	    	/*else if(user.getGrade() == Grade.admin) {
	    		System.out.println("1");
	    		loader.setLocation(MainApp.class.getResource("view/AttributionMac_P.fxml"));
	    		
	    		/ *AnchorPane page = (AnchorPane) loader.load();
		    	rootLayout.setCenter(page);
		    	
		    	GestPageController_P controller = loader.getController();
		    	//GestPageController_P controller = loader.setController(new GestPageController_P());
		    	controller.setMainApp(this); //developper cette fonction* /
	    	}*/
	    	else if(user.getGrade() == Grade.user) { //Affiche la fenetre pour les utilisateurs
	    		loader.setLocation(MainApp.class.getResource("view/FenetreUtilisateur_P.fxml"));
	    		
	    		AnchorPane page = (AnchorPane) loader.load();
		    	rootLayout.setCenter(page);
		    	
		    	UserPageController_P controller = loader.getController();
		    	controller.setMainApp(this);
	    	}
    	} catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Retourne le Stage principal
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    
    public ObservableList<User_P> getUserData() {
    	return userData;
    }
    
    public void addUser(User_P user) {
    	System.out.println("On ajoute un user");
    	userData.add(user);
    }

    @Override
    public void stop(){ //Lors de la fermeture de l'application, on remet l'adresse MAC déjà présente sur le pc
        AttribMacController_P attribMac = new AttribMacController_P();
        attribMac.setMainApp(this);
        attribMac.resetMac();
    }
    
    public String getDefaultAdrMac() {
		return defaultAdrMac;
	}

	public void setDefaultAdrMac(String defaultAdrMac) {
		this.defaultAdrMac = defaultAdrMac;
	}

	public String getNomInterfaceEth() {
		return nomInterfaceEth;
	}

	public void setNomInterfaceEth(String nomInterfaceEth) {
		this.nomInterfaceEth = nomInterfaceEth;
	}

	public static void main(String[] args) {
        launch(args);
    }
}
