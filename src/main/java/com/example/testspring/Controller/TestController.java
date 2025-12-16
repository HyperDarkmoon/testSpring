package com.example.testspring.Controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.testspring.Entity.Categorie;
import com.example.testspring.Entity.Client;
import com.example.testspring.Entity.Colis;
import com.example.testspring.Entity.Livreur;
import com.example.testspring.Service.ServicesInterfaces;
import com.example.testspring.Repository.ColisRepo;

@RestController
@RequestMapping("/api")
public class TestController {

	private final ServicesInterfaces services;
	private final ColisRepo colisRepo;

	public TestController(ServicesInterfaces services, ColisRepo colisRepo) {
		this.services = services;
		this.colisRepo = colisRepo;
	}

	@PostMapping("/AjouterClient")
	public Client ajouterClient(@RequestBody Client client) {
		return services.ajouterClient(client);
	}

	@PostMapping("/AjouterColis/{clientId}")
	public Colis ajouterColis(@RequestBody Colis colis, @PathVariable("clientId") Long clientId) {
		return services.ajouterColisEtAffecterColisAClient(colis, clientId);
	}

	@PostMapping("/AjouterLivreur")
	public void ajouterLivreur(@RequestBody LivreurAssignRequest request) {
		Livreur livreur;
		if (request.getLivreur() != null) {
			livreur = request.getLivreur();
		} else {
			livreur = new Livreur();
			livreur.setNomLivreur(request.getNomLivreur());
			livreur.setPrenomLivreur(request.getPrenomLivreur());
		}
		services.ajouterLivreurEtAffecterColisALivreur(livreur, request.getRefColis());
	}

	@GetMapping("/colis/etat")
	public List<Colis> mettreAJourEtat() {
		services.mettreAJourEtAfficherColis();
		return colisRepo.findAll();
	}

	@GetMapping("/clients")
	public List<Client> afficherClients(@RequestParam String categorie,
										@RequestParam String dateLivraison) {
		Categorie cat = Categorie.valueOf(categorie.trim().toUpperCase());
		LocalDate date = parseDate(dateLivraison);
		return services.afficherClients(cat, date);
	}

	@GetMapping("/colis/{reference}/montant")
	public float montant(@PathVariable("reference") String reference) {
		return services.montantAPayerParClient(reference);
	}

	@GetMapping("/clients/fidele")
	public Client clientFidele() {
		return services.clientFidele();
	}

	private LocalDate parseDate(String input) {
		try {
			return LocalDate.parse(input, DateTimeFormatter.ISO_LOCAL_DATE);
		} catch (DateTimeParseException e) {
			return LocalDate.parse(input, DateTimeFormatter.ofPattern("yyyy/MM/dd"));
		}
	}
}

class LivreurAssignRequest {
	private Livreur livreur;
	private String nomLivreur;
	private String prenomLivreur;
	private List<String> refColis;

	public Livreur getLivreur() {
		return livreur;
	}

	public void setLivreur(Livreur livreur) {
		this.livreur = livreur;
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

	public List<String> getRefColis() {
		return refColis;
	}

	public void setRefColis(List<String> refColis) {
		this.refColis = refColis;
	}
}
