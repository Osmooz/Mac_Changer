package ch.makery.address.view;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import ch.makery.address.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;

public class AttribMacController_P {

	    @FXML
	    private Button bAttribMac;
	    @FXML
	    private Button bReinitAdrMac;
	    @FXML
	    private CheckBox cbModifAdrSurPc;
	    @FXML
	    private Label lAdrMac;
	    @FXML
	    
	    private MainApp mainApp;
	    
	    public static String defaultAdrMac = "";
	    public static String interfaceEth = "";
	    
	    private static boolean hasMacChange = false;
	    
	    public AttribMacController_P() {
	    	
	    }

	    @FXML
	    private void initialize() {
	    }

	    @FXML	
	    public void attribMac() {
	    	hasMacChange = true;
	    	String OS = System.getProperty("os.name").toLowerCase();
	 
	    	//On enregistre l'adresse MAC qui est pour le moment sur le pc
	    	if(OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 ) { //On est sur linux
    			StringBuffer output = new StringBuffer();
    			Process p;
    			try {
    				String[] cmd = {"/bin/sh", "-c", "ifconfig eth0 | awk '/HWaddr/ {print $5}'"};
    				p = Runtime.getRuntime().exec(cmd);
    				p.waitFor();
    				BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

    	            String line = "";
    				while ((line = reader.readLine())!= null) {
    					output.append(line + "\n");
    				}

    			} catch (Exception e) {
    				e.printStackTrace();
    			}
    			defaultAdrMac = output.toString(); //On sauvegarde l'adresse MAC actuelle dans la variable globale "defaultAdrMac" de la classe MainApp
    			System.out.println("L'adresse MAC de base : " + defaultAdrMac);
	    	}
	    	
	    	
	    	if(cbModifAdrSurPc.isSelected()) { //Si on doit modifier directement l'adresse MAC sur le pc
	    		System.out.println("Le check bouttum a été validé");
	    		System.out.println("Le systeme d'exploitation est : " + System.getProperty("os.name"));
	    		
	    		if(OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 ) { //Os de la machine est Linux
	    			//On Cherche le nom de l'interface ethernet
	    			StringBuffer output = new StringBuffer();
	    			Process p;
	    			try {
	    				String[] cmd = {"/bin/sh", "-c", "lshw -C network | grep -A 5 Ethernet\\ interface | sed '1,5d' | sed 's/.* //'"};
	    				p = Runtime.getRuntime().exec(cmd);
	    				p.waitFor();
	    				BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
	    				String line = "";
	    				while ((line = reader.readLine())!= null) {
	    					output.append(line + "\n");
	    				}

	    			} catch (Exception e) {
	    				e.printStackTrace();
	    			}
	    			
	    			interfaceEth = output.toString();
	    			System.out.println("L'interface filaire est : " + interfaceEth);	    			
	    			//On change l'adresse MAC
	    			try {
	    				p = Runtime.getRuntime().exec("ifconfig " + interfaceEth + " down");
	    				p.waitFor();
	    				p = Runtime.getRuntime().exec("ifconfig  " + interfaceEth + " hw ether "/**** + ADR MAC DE LA BD ****/); //TODO: Rajouter à la place de l'adresse mac marquée une adresse mac de la BD qui est disponible
	    				p.waitFor();
	    				p = Runtime.getRuntime().exec("ifconfig " + interfaceEth + " up");
	    				p.waitFor();
	    				p = Runtime.getRuntime().exec("/etc/init.d/networking restart");
	    				p.waitFor();
	    			} catch (Exception e) {
	    				e.printStackTrace();
	    			}
	    			lAdrMac.setText("BLABLABLA"); //TODO: mettre la même adresse MAC à la place de "BLABLABLA" que celle d'au dessus
	    		}
	    		else if(OS.indexOf("win") >= 0) {//OS de la machine est Windows
	    			Process p;
	    			try {
	    				p = Runtime.getRuntime().exec("reg add HKLM SYSTEM\\CurrentControlSet\\Control\\Class\\{4D36E972-E325-11CE-BFC1-08002BE10318}\0001] /v NetworkAddress /d " /*+ transformAdrMacForWindows(*****)*/); //Remplacer 0001 avec le numéro d'interface et mettre comme param dans transformAdrMacForWindows l'adress mac obtenue avec la BD
	    				p.waitFor();
	    			} catch (Exception e) {
	    				e.printStackTrace();
	    			}
	    		}
	    		else if(OS.indexOf("mac") >= 0) {//OS est MAC
	    			
	    		}
	    		else { //On ne reconnait pas l'OS
	    			Alert alert = new Alert(AlertType.WARNING);
	                alert.initOwner(mainApp.getPrimaryStage());
	                alert.setTitle("Erreur");
	                alert.setHeaderText("OS inconnu");
	                alert.setContentText("Votre OS n'est pas répertorié pour notre logiciel, veuillé contacter un administrateur.");

	                alert.showAndWait();
	    		}
	    	}
	    	else { //On doit juste afficher l'adresse MAC dans le LABEL
	    		lAdrMac.setText("BLABLABLA"); //TODO: mettre une adresse mac disponible à la place de "BLABLABLA"
	    	}
	    }
	    
	    public void resetMac() { //Remet l'adresse MAC présente avant le changement d'adresse MAC
    		if(hasMacChange) {
	    		System.out.println("Le systeme d'exploitation est : " + System.getProperty("os.name"));
	    		String OS = System.getProperty("os.name").toLowerCase();
	    		if(OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 ) { //On est sur linux
	    			Process p;
	    			System.out.println("On remet l'adresse MAC de base : " + defaultAdrMac + " sur l'interface : " + interfaceEth);
	    			try {
	    				p = Runtime.getRuntime().exec("ifconfig " + interfaceEth + " down");
	    				p.waitFor();
	    				p = Runtime.getRuntime().exec("ifconfig " + interfaceEth + " hw ether " + defaultAdrMac);
	    				p.waitFor();
	    				p = Runtime.getRuntime().exec("ifconfig " + interfaceEth + " up");
	    				p.waitFor();
	    				p = Runtime.getRuntime().exec("/etc/init.d/networking restart");
	    				p.waitFor();
	    			} catch (Exception e) {
	    				e.printStackTrace();
	    			}
	    		}
	    		else if(OS.indexOf("win") >= 0) {//OS est Windows
	    			Process p;
	    			try {
	    				p = Runtime.getRuntime().exec("reg add HKLM SYSTEM\\CurrentControlSet\\Control\\Class\\{4D36E972-E325-11CE-BFC1-08002BE10318}\0001] /v NetworkAddress /d " /**** + trouver le moyen de récupérer l'adresse MAC sur windows ****/ ); //Remplacer 0001 avec le numéro d'interface et faire ce qui est marqué juste avant
	    				p.waitFor();
	    			} catch (Exception e) {
	    				e.printStackTrace();
	    			}
	    		}
	    		else if(OS.indexOf("mac") >= 0) {//OS est MAC
	    			
	    		}
	    		else { //On ne reconnait pas l'OS 
	    			Alert alert = new Alert(AlertType.WARNING);
	                alert.initOwner(mainApp.getPrimaryStage());
	                alert.setTitle("Erreur");
	                alert.setHeaderText("OS inconnu");
	                alert.setContentText("Votre OS n'est pas répertorié pour notre logiciel, veuillez contacter un administrateur.");
	
	                alert.showAndWait();
	    		}
	    		lAdrMac.setText("");
	    		hasMacChange = false;
    		}
	    }
	    
	    public String transformAdrMacForWindows (String adrMac) {
	    	String adrMacWindows = "";
	    	for(int index = 0 ; index < adrMac.length() ; index++) {
				if(adrMac.charAt(index) != ':') {
					adrMacWindows += adrMac.charAt(index); 
				}
			}
	    	return adrMacWindows;
	    }
	    
	    public void setMainApp(MainApp mainApp) {
	        this.mainApp = mainApp;
	    }
	
}
