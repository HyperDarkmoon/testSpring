package com.example.testspring.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.testspring.Entity.Client;

public interface ClientRepo extends JpaRepository<Client, Long> {
}
