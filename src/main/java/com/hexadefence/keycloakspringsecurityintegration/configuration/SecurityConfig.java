package com.hexadefence.keycloakspringsecurityintegration.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/api/public").permitAll()
                .antMatchers("/api/private").authenticated()
            .and()
                .oauth2Login();
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository(){
        return new InMemoryClientRegistrationRepository(keycloakClientRegistration());
    }

    private ClientRegistration keycloakClientRegistration(){
        return ClientRegistration.withRegistrationId("keycloak") // registration_id
                .clientId("spring-security-client")
                .clientSecret("15407cfb-21fe-4540-ab47-51a0356e04b8")
                .scope("openid")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)

                // {baseUrl}/login/oauth2/code/{registration_id}
                .redirectUri("http://localhost:9090/login/oauth2/code/keycloak")
                .authorizationUri("http://localhost:8080/auth/realms/master/protocol/openid-connect/auth")
                .tokenUri("http://localhost:8080/auth/realms/master/protocol/openid-connect/token")
                .userInfoUri("http://localhost:8080/auth/realms/master/protocol/openid-connect/userinfo")
                .jwkSetUri("http://localhost:8080/auth/realms/master/protocol/openid-connect/certs")
                .userNameAttributeName(IdTokenClaimNames.SUB)
                .clientName("Keycloak")
                .build();
    }
}
