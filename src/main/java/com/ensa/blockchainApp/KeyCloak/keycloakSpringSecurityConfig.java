package com.ensa.blockchainApp.KeyCloak;

import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

//2
@KeycloakConfiguration
class keycloakSpringSecurityConfig extends KeycloakWebSecurityConfigurerAdapter {

	@Override
	protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
		return new RegisterSessionAuthenticationStrategy ( new SessionRegistryImpl (  ) );
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth){
		auth.authenticationProvider ( keycloakAuthenticationProvider () );
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		super.configure ( http );
		http.authorizeRequests ().antMatchers ("/tracabilite" ).hasAuthority ( "ADMIN" ); //or authenticated ()!
		http.authorizeRequests ().antMatchers ("/UsersList" ).hasAuthority ( "ADMIN" ); //or hasAuthority !
		http.authorizeRequests ().antMatchers ("/InspectorsList" ).hasAuthority ( "ADMIN" );
		http.authorizeRequests ().antMatchers ("/addUser" ).hasAuthority ( "ADMIN" );
		http.authorizeRequests ().antMatchers ("/addInspector" ).hasAuthority ( "ADMIN" );
		http.authorizeRequests ().antMatchers ("/deleteUser/{id}" ).hasAuthority ( "ADMIN" );
		http.authorizeRequests ().antMatchers ("/getUser" ).hasAuthority ( "ADMIN" );
		http.authorizeRequests ().antMatchers ("/editUser" ).hasAuthority ( "ADMIN" );


		http.authorizeRequests ().antMatchers ("/addReclamation" ).hasAuthority ( "USER" );
		http.authorizeRequests ().antMatchers ("/voirReclamations" ).authenticated ();

		http.authorizeRequests ().antMatchers ("/signaler" ).hasAuthority ( "INSPECTOR" );
	}
}
