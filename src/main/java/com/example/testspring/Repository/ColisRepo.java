package com.example.testspring.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.testspring.Entity.Categorie;
import com.example.testspring.Entity.Colis;

public interface ColisRepo extends JpaRepository<Colis, Long> {
	Optional<Colis> findByReferenceColis(String referenceColis);
	List<Colis> findByProduits_CategorieProdAndDateLivraison(Categorie categorieProd, LocalDate dateLivraison);
}
