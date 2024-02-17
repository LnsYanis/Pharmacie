package com.Pharmacie.PH.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.Pharmacie.PH.Service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .cors().and()
            .csrf().disable()
            .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers(HttpMethod.POST, "/api/inscription").permitAll()
                .antMatchers(HttpMethod.GET, "/api/Produit/liste").permitAll()
                .antMatchers(HttpMethod.POST, "/api/connexion").permitAll()
                .antMatchers(HttpMethod.POST, "/api/connexion/deconnexion").permitAll()
                .antMatchers(HttpMethod.POST, "/api/Produit/AjoutProduit").permitAll()
                .antMatchers(HttpMethod.GET, "/api/connexion/client/details/{email}").permitAll()
                .antMatchers(HttpMethod.POST, "/api/panier/ajouter").permitAll()
                .antMatchers(HttpMethod.GET, "/api/panier/commandes-client/{clientId}").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/panier/supprimer-commande/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/panier/supprimer-panier/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/Produit/{productId}").permitAll()
                .antMatchers(HttpMethod.POST, "/api/facture/Ajouter-Facture/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/facture/recuperer/{factureId}").permitAll()
                .antMatchers(HttpMethod.GET, "/api/Role/{clientId}").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/Produit//decrementer-quantite/{nomProduit}/{quantiteDecrementee}").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .and()
            .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
