package com.example.testspring.Entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Produit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idProduit;

	private String identifiant;

	@Enumerated(EnumType.STRING)
	private Categorie categorieProd;

	@ManyToMany(mappedBy = "produits")
	private Set<Colis> colis = new HashSet<>();

	public Produit() {
	}

	public Produit(Long idProduit, String identifiant, Categorie categorieProd) {
		this.idProduit = idProduit;
		this.identifiant = identifiant;
		this.categorieProd = categorieProd;
	}

	public Long getIdProduit() {
		return idProduit;
	}

	public void setIdProduit(Long idProduit) {
		this.idProduit = idProduit;
	}

	public String getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}

	public Categorie getCategorieProd() {
		return categorieProd;
	}

	public void setCategorieProd(Categorie categorieProd) {
		this.categorieProd = categorieProd;
	}

	public Set<Colis> getColis() {
		return colis;
	}

	public void setColis(Set<Colis> colis) {
		this.colis = colis;
	}
}

