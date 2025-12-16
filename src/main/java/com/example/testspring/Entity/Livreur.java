package com.example.testspring.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Livreur {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idLivreur;

	private String nomLivreur;
	private String prenomLivreur;

	public Livreur() {
	}

	public Livreur(Long idLivreur, String nomLivreur, String prenomLivreur) {
		this.idLivreur = idLivreur;
		this.nomLivreur = nomLivreur;
		this.prenomLivreur = prenomLivreur;
	}

	public Long getIdLivreur() {
		return idLivreur;
	}

	public void setIdLivreur(Long idLivreur) {
		this.idLivreur = idLivreur;
	}

	public String getNomLivreur() {
		return nomLivreur;
	}

	public void setNomLivreur(String nomLivreur) {
		this.nomLivreur = nomLivreur;
	}

	public String getPrenomLivreur() {
		return prenomLivreur;
	}

	public void setPrenomLivreur(String prenomLivreur) {
		this.prenomLivreur = prenomLivreur;
	}
}
