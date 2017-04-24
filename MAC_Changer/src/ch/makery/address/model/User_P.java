package ch.makery.address.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User_P {

	public enum Grade {
		admin,
		user;
	}
	
	private final StringProperty pseudo;
	private final StringProperty email;
	private final StringProperty mdp;
	private Grade grade;
	
	public User_P(String pseudo, String email, String mdp) {
		this.pseudo = new SimpleStringProperty(pseudo);
		this.email = new SimpleStringProperty(email);
		this.mdp = new SimpleStringProperty(mdp);
		this.grade = Grade.user;
	}

	public StringProperty getPseudo() {
		return pseudo;
	}
	
	public void setPseudo(String pseudo) {
		this.pseudo.set(pseudo);
	}

	public StringProperty getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email.set(email);
	}

	public StringProperty getMdp() {
		return mdp;
	}
	
	public void setMdp(String mdp) {
		this.mdp.set(mdp);
	}
	
	public Grade getGrade() {
		return grade;
	}
	
	public void setGrade(Grade grade) {
		this.grade = grade	;
	}
}
