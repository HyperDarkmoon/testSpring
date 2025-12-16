package com.example.testspring.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.testspring.Entity.Livreur;

public interface LivreurRepo extends JpaRepository<Livreur, Long> {
}
