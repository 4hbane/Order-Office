package com.ensa.blockchainApp.KeyCloak;

import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//1
@Configuration
class KeyloackConfig{
	@Bean
    KeycloakSpringBootConfigResolver configResolver(){
			return  new KeycloakSpringBootConfigResolver ();
	}
}
