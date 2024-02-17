package com.Pharmacie.PH.Repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Pharmacie.PH.Model.Client;
import com.Pharmacie.PH.Model.Panier;
@Repository
public interface PanierRepository extends JpaRepository<Panier, String> {

	Optional<Panier> findByClientAndNom(Client client, String nomProduit);
	List<Panier> findByClient_Id(String clientId);


	 @Transactional
	    @Modifying
	    @Query("DELETE FROM Panier p WHERE p.client.id = :clientId AND p.nom = :nomProduit")
	    void supprimerCommandeByIdAndNom(@Param("clientId") String clientId, @Param("nomProduit") String nomProduit);
	 
	 @Transactional
	    void deleteByClientId(String clientId);
}



