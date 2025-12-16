package com.example.testspring.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.scheduling.annotation.Scheduled;

import com.example.testspring.Entity.Categorie;
import com.example.testspring.Entity.Client;
import com.example.testspring.Entity.Colis;
import com.example.testspring.Entity.Etat;
import com.example.testspring.Entity.Livreur;
import com.example.testspring.Entity.Produit;
import com.example.testspring.Repository.ClientRepo;
import com.example.testspring.Repository.ColisRepo;
import com.example.testspring.Repository.LivreurRepo;
import com.example.testspring.Repository.ProduitRepo;

@Service
@Transactional
public class ServicesImp implements ServicesInterfaces {

	private final ClientRepo clientRepo;
	private final ColisRepo colisRepo;
	private final LivreurRepo livreurRepo;
	private final ProduitRepo produitRepo;

	public ServicesImp(ClientRepo clientRepo, ColisRepo colisRepo, LivreurRepo livreurRepo, ProduitRepo produitRepo) {
		this.clientRepo = clientRepo;
		this.colisRepo = colisRepo;
		this.livreurRepo = livreurRepo;
		this.produitRepo = produitRepo;
	}

	@Override
	public Client ajouterClient(Client cl) {
		return clientRepo.save(cl);
	}

	@Override
	public Colis ajouterColisEtAffecterColisAClient(Colis c, Long idClient) {
		Client client = clientRepo.findById(idClient)
			.orElseThrow(() -> new IllegalArgumentException("Client introuvable pour id=" + idClient));

		// Persist produits first and maintain both sides of the relation
		Set<Produit> produits = c.getProduits() == null ? new HashSet<>() : new HashSet<>(c.getProduits());
		Set<Produit> managedProduits = new HashSet<>();
		for (Produit p : produits) {
			Produit saved = produitRepo.save(p);
			saved.getColis().add(c);
			managedProduits.add(saved);
		}

		c.setClient(client);
		c.setProduits(managedProduits);

		Colis savedColis = colisRepo.save(c);

		// Maintain bidirectional list on client
		List<Colis> clientColis = client.getColis();
		if (clientColis == null) {
			clientColis = new ArrayList<>();
			client.setColis(clientColis);
		}
		clientColis.add(savedColis);

		return savedColis;
	}

	@Override
	public void ajouterLivreurEtAffecterColisALivreur(Livreur l, List<String> refColis) {
		Livreur livreur = livreurRepo.save(l);

		if (refColis == null) {
			return;
		}

		for (String ref : refColis) {
			Optional<Colis> colisOpt = colisRepo.findByReferenceColis(ref);
			if (colisOpt.isPresent()) {
				Colis colis = colisOpt.get();
				colis.setLivreur(livreur);
				colisRepo.save(colis);
			}
		}
	}

	@Override
	@Scheduled(fixedRate = 20000)
	public void mettreAJourEtAfficherColis() {
		List<Colis> colisList = colisRepo.findAll();
		LocalDate today = LocalDate.now();
		for (Colis colis : colisList) {
			if (colis.getEtatColis() == Etat.EN_ATTENTE && colis.getLivreur() != null
					&& colis.getDateLivraison() != null && colis.getDateLivraison().isBefore(today)) {
				colis.setEtatColis(Etat.EN_COURS);
				colisRepo.save(colis);
			}
			System.out.println("Colis ref=" + colis.getReferenceColis() + " etat=" + colis.getEtatColis());
		}
	}

	@Override
	public List<Client> afficherClients(Categorie cateGorieProd, LocalDate dateLivraison) {
		List<Colis> colis = colisRepo.findByProduits_CategorieProdAndDateLivraisonAfter(cateGorieProd, dateLivraison);
		return colis.stream()
				.map(Colis::getClient)
				.filter(Objects::nonNull)
				.distinct()
				.collect(Collectors.toList());
	}

	@Override
	public float montantAPayerParClient(String referenceColis) {
		return colisRepo.findByReferenceColis(referenceColis)
				.map(colis -> {
					float prix = colis.getPrixCommande() == null ? 0f : colis.getPrixCommande();
					float frais = prix > 100f ? 0f : 8f;
					return prix + frais;
				})
				.orElse(0f);
	}

	@Override
	public Client clientFidele() {
		return clientRepo.findAll().stream()
				.max((c1, c2) -> Integer.compare(
						c1.getColis() == null ? 0 : c1.getColis().size(),
						c2.getColis() == null ? 0 : c2.getColis().size()))
				.orElse(null);
	}
}
