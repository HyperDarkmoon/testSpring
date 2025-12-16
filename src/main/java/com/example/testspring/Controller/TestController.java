package com.example.testspring.Controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	public void ajouterLivreur(@RequestBody Livreur livreur, @RequestParam(required = false) List<String> refColis) {
		services.ajouterLivreurEtAffecterColisALivreur(livreur, refColis);
	}

	@PutMapping("/colis/etat")
	public List<Colis> mettreAJourEtat() {
		services.mettreAJourEtAfficherColis();
		return colisRepo.findAll();
	}

	@GetMapping("/clients")
	public List<Client> afficherClients(@RequestParam Categorie categorie,
										@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateLivraison) {
		return services.afficherClients(categorie, dateLivraison);
	}

	@GetMapping("/colis/{reference}/montant")
	public float montant(@PathVariable("reference") String reference) {
		return services.montantAPayerParClient(reference);
	}

	@GetMapping("/clients/fidele")
	public Client clientFidele() {
		return services.clientFidele();
	}
}
