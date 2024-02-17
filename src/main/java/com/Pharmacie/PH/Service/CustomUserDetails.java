package com.Pharmacie.PH.Service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUserDetails extends User {

    private final String clientId;

    public CustomUserDetails(String clientId, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.clientId = clientId;
    }

    public String getClientId() {
        return clientId;
    }
}
