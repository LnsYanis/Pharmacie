package com.Pharmacie.PH.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

import com.Pharmacie.PH.Model.Client;
import com.Pharmacie.PH.Repository.ClientRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Client> clientOptional = clientRepository.findByEmail(email);

        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();
            System.out.println("ID du client chargé depuis la base de données : " + client.getId());

            return new CustomUserDetails(
                client.getId(),
                client.getEmail(),
                client.getMdp(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
            );
        } else {
            throw new UsernameNotFoundException("Utilisateur non trouvé avec l'email: " + email);
        }
    }

}
