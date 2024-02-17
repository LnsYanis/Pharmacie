package com.Pharmacie.PH.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Pharmacie.PH.Model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
    boolean existsByEmail(String email);
    Optional<Client> findByEmail(String email); 
    Optional<Client> findByNom(String Nom); 
    Optional<Client> findByEmailAndMdp(String email, String mdp);
	
}
