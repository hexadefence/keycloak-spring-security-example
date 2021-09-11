package com.hexadefence.keycloakspringsecurityintegration;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SimpleController {

    @GetMapping("/public")
    public String publicEndpoint(){
        return "Response from public endpoint";
    }

    @GetMapping("/private")
    public String privateEndpoint(){
        DefaultOidcUser oidcUser =
                (DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return "Response from private endpoint";
    }

}
