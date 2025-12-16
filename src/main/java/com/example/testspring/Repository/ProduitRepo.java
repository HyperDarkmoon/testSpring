package com.example.testspring.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.testspring.Entity.Produit;

public interface ProduitRepo extends JpaRepository<Produit, Long> {
}
