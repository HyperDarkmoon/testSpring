package com.example.testspring.Entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Client {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idClient;

	private String nomClient;
	private String numTel;

	@OneToMany(mappedBy = "client")
	private List<Colis> colis = new ArrayList<>();

	public Client() {
	}

	public Client(Long idClient, String nomClient, String numTel) {
		this.idClient = idClient;
		this.nomClient = nomClient;
		this.numTel = numTel;
	}

	public Long getIdClient() {
		return idClient;
	}

	public void setIdClient(Long idClient) {
		this.idClient = idClient;
	}

	public String getNomClient() {
		return nomClient;
	}

	public void setNomClient(String nomClient) {
		this.nomClient = nomClient;
	}

	public String getNumTel() {
		return numTel;
	}

	public void setNumTel(String numTel) {
		this.numTel = numTel;
	}

	public List<Colis> getColis() {
		return colis;
	}

	public void setColis(List<Colis> colis) {
		this.colis = colis;
	}
}
