package com.Pharmacie.PH;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import com.Pharmacie.PH.Service.ProduitService;

import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.PostConstruct;

@SpringBootApplication
@EntityScan(basePackages = "com.Pharmacie.PH.Model")
public class PhApplication {

    @Autowired
    private ProduitService produitService;

    public static void main(String[] args) {
        SpringApplication.run(PhApplication.class, args);
    }

    @PostConstruct
    public void init() {
       
        produitService.genererProduitsPharmaceutiques();
    }
}
