package com.example.mi_api.Class;

public class Usuario extends ShowItem {

	private String mail;
	private String contra;

	// Constructor
	public Usuario(String mail, String contra) {
		this.mail = mail;
		this.contra = contra;
	}

	// Getters & Setters
	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getContra() {
		return contra;
	}

	public void setContra(String contra) {
		this.contra = contra;
	}

}