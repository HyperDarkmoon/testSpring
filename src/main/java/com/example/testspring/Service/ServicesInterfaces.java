package com.example.testspring.Service;

import com.example.testspring.Entity.Categorie;
import com.example.testspring.Entity.Client;
import com.example.testspring.Entity.Colis;
import com.example.testspring.Entity.Livreur;

import java.time.LocalDate;
import java.util.List;

public interface ServicesInterfaces {
    Client ajouterClient(Client cl);
    Colis ajouterColisEtAffecterColisAClient(Colis c, Long idClient);
    void ajouterLivreurEtAffecterColisALivreur(Livreur l, List<String> refColis);
    void mettreAJourEtAfficherColis();
    List<Client> afficherClients(Categorie cateGorieProd, LocalDate dateLivraison);
    float montantAPayerParClient(String referenceColis);
    Client clientFidele();
}
