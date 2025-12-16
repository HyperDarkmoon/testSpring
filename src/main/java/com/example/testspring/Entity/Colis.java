package com.example.testspring.Entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinTable;

@Entity
public class Colis {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idColis;

	private String referenceColis;
	private Float prixCommande;
	private LocalDate dateLivraison;

	@Enumerated(EnumType.STRING)
	private Etat etatColis;

	@ManyToOne
	@JoinColumn(name = "client_id")
	private Client client;

	@ManyToOne
	@JoinColumn(name = "livreur_id")
	private Livreur livreur;

	@ManyToMany
	@JoinTable(
		name = "colis_produits",
		joinColumns = @JoinColumn(name = "colis_id"),
		inverseJoinColumns = @JoinColumn(name = "produit_id")
	)
	private Set<Produit> produits = new HashSet<>();

	public Colis() {
	}

	public Colis(Long idColis, String referenceColis, Float prixCommande, LocalDate dateLivraison, Etat etatColis) {
		this.idColis = idColis;
		this.referenceColis = referenceColis;
		this.prixCommande = prixCommande;
		this.dateLivraison = dateLivraison;
		this.etatColis = etatColis;
	}

	public Long getIdColis() {
		return idColis;
	}

	public void setIdColis(Long idColis) {
		this.idColis = idColis;
	}

	public String getReferenceColis() {
		return referenceColis;
	}

	public void setReferenceColis(String referenceColis) {
		this.referenceColis = referenceColis;
	}

	public Float getPrixCommande() {
		return prixCommande;
	}

	public void setPrixCommande(Float prixCommande) {
		this.prixCommande = prixCommande;
	}

	public LocalDate getDateLivraison() {
		return dateLivraison;
	}

	public void setDateLivraison(LocalDate dateLivraison) {
		this.dateLivraison = dateLivraison;
	}

	public Etat getEtatColis() {
		return etatColis;
	}

	public void setEtatColis(Etat etatColis) {
		this.etatColis = etatColis;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Livreur getLivreur() {
		return livreur;
	}

	public void setLivreur(Livreur livreur) {
		this.livreur = livreur;
	}

	public Set<Produit> getProduits() {
		return produits;
	}

	public void setProduits(Set<Produit> produits) {
		this.produits = produits;
	}
}
