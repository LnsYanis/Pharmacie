package com.Pharmacie.PH.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Pharmacie.PH.Model.Facture;

@Repository
public interface FactureRepository extends JpaRepository<Facture,Long> {

}
