package com.Pharmacie.PH.Controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Pharmacie.PH.Request.ConnexionRequest;
import com.Pharmacie.PH.Request.InscriptionRequest;
import com.Pharmacie.PH.Model.Client;
import com.Pharmacie.PH.Repository.ClientRepository;
import com.Pharmacie.PH.Service.ClientService;
import com.Pharmacie.PH.Service.CustomUserDetails;
import com.Pharmacie.PH.Service.CustomUserDetailsService;

@RestController
@RequestMapping("/api")
public class ClientController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final UserDetailsService userDetailsService;
    private final ClientService clientService;
    private final BCryptPasswordEncoder bcryptEncoder;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    public ClientController(AuthenticationManager authenticationManager,
            UserDetailsService userDetailsService,
            ClientService clientService,
            BCryptPasswordEncoder bcryptEncoder,
            CustomUserDetailsService customUserDetailsService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.clientService = clientService;
        this.bcryptEncoder = bcryptEncoder;
        this.customUserDetailsService = customUserDetailsService;
    }

    @PostMapping("/inscription")
    public ResponseEntity<Map<String, String>> sInscrire(@RequestBody InscriptionRequest request,
            HttpServletRequest httpRequest) {
        Optional<Client> existingClient = clientService.existeEmail(request.getEmail());
        if (existingClient.isPresent()) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "L'adresse e-mail existe déjà");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        }
        Client client = new Client();
        client.setNom(request.getNom());
        client.setPrenom(request.getPrenom());
        client.setMdp(bcryptEncoder.encode(request.getMdp()));
        client.setEmail(request.getEmail());
        client.setRole(request.getRole());
        String clientId = clientService.inscrire(client);
        autoLogin(client.getEmail(), request.getMdp(), httpRequest);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Inscription réussie");
        response.put("clientId", clientId);
        return ResponseEntity.ok().body(response);
    }
    @GetMapping(value = "/Role/{clientId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> getRoleById(@PathVariable String clientId) {
      

        Optional<Client> clientOptional = clientRepository.findById(clientId);

        if (clientOptional.isPresent()) {
            
            String role = clientOptional.get().getRole();

            Map<String, String> response = new HashMap<>();
            response.put("role", role);

            return ResponseEntity.ok(response);
        } else {
           
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Client non trouvé");

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
    private void autoLogin(String username, String password, HttpServletRequest request) {
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            
        }
    }

    @PostMapping("/connexion")
    public ResponseEntity<Map<String, String>> seConnecter(@RequestBody ConnexionRequest request) {
        String clientId = getClientIdByEmail(request.getEmail());

        if (verifierIdentifiants(request.getEmail(), request.getmdp()) && clientId != null) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Connexion réussie");
            response.put("clientId", clientId);
            return ResponseEntity.ok(response);
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Identifiants incorrects. Veuillez réessayer.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }

    // Méthode pour récupérer l'ID du client par son email
    private String getClientIdByEmail(String email) {
        Optional<Client> clientOptional = clientService.existeEmail(email);
        return clientOptional.map(Client::getId).orElse(null);
    }



    private boolean verifierIdentifiants(String email, String mdp) {
        Optional<Client> clientOptional = clientRepository.findByEmail(email);
        return clientOptional.isPresent() && bcryptEncoder.matches(mdp, clientOptional.get().getMdp());
    }

    @PostMapping("/connexion/deconnexion")
    public ResponseEntity<String> seDeconnecter(HttpServletRequest request) {
        SecurityContextHolder.clearContext();
        request.getSession().invalidate();
        JSONObject json = new JSONObject();
        json.put("message", "Déconnexion réussie");
        return ResponseEntity.ok(json.toString());
    }

    @GetMapping("/connexion/client/details/{email}")
    public ResponseEntity<CustomUserDetails> getClientDetails(@PathVariable String email) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
        CustomUserDetails customUserDetails = (CustomUserDetails) userDetails;
        return ResponseEntity.ok(customUserDetails);
    }
}
