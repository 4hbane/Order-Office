package com.ensa.blockchainApp.Controller;

import com.ensa.blockchainApp.Business.User;
import com.ensa.blockchainApp.KeyCloak.keyCloakUser;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.*;
import static org.codehaus.groovy.runtime.InvokerHelper.asList;


@Controller
// This the controller which manages getting/ deleting and editing users of the keycloak server !
public class UsersManagementController {

    private String url = "http://localhost:8080/auth";
    private String realm = "blokchain-realm";
    private String username = "adminapp";
    private String password = "abdou";
    private String clientId = "admin-cli";
    private boolean isInspector;
    Keycloak kc = KeycloakBuilder.builder()
            .serverUrl(url)
            .realm(realm)
            .username(username)
            .password(password)
            .clientId(clientId)
            .resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build())
            .build();

    RealmResource realmResource = kc.realm(realm );
    UsersResource usersRessource = realmResource.users();

    // Check the diff between User and keycloakUser !
    // This list is  for getting all the users in our keycloak server !
    List<keyCloakUser> users = new ArrayList<>();

    // getting the users with roles USER || INSPECTOR!
    public List<keyCloakUser> getAllUsersFromKeycloak() {
        usersRessource.list ().forEach ( userRepresentation -> {
            keyCloakUser temp = new keyCloakUser ();
            temp.setId ( userRepresentation.getId () );
            temp.setUserName ( userRepresentation.getUsername () );
            temp.setFirstName ( userRepresentation.getFirstName () );
            temp.setLastName ( userRepresentation.getLastName () );
            temp.setEmail ( userRepresentation.getEmail () );
            List<RoleRepresentation> Roles = usersRessource.get ( userRepresentation.getId () ).roles ().realmLevel ().listEffective ();
            final StringBuilder rolee = new StringBuilder (  );
            Roles.forEach ( role->{
                if (role.getName ().equals ( "USER" )){
                    rolee.append ( "USER");
                }
                else if (role.getName ().equals ( "INSPECTOR" )) {
                    rolee.append ( "INSPECTOR" );
                }
            } );
            temp.setType (String.valueOf (rolee));

                if (temp.getType ().equals ( "USER" ) || temp.getType ().equals ( "INSPECTOR" )) {
                        users.add ( temp );
                }
            });

        return users;
    }

    // getting the users with the role INSPECTOR!
    public List<keyCloakUser> getInspectors() {
        List<keyCloakUser> inspectors = new ArrayList<> (  );
        getAllUsersFromKeycloak ().forEach ( user->{
            if (user.getType ().equals ( "INSPECTOR")){
                inspectors.add ( user );

            }
        } );
        getAllUsersFromKeycloak ().clear ();
        return inspectors;
    }
    // getting the users with the role USER!
    public List<keyCloakUser> getUsers() {
        List<keyCloakUser> utilisateurs = new ArrayList<> (  );
        getAllUsersFromKeycloak ().forEach ( user->{
            if (user.getType ().equals ("USER")){
                utilisateurs.add ( user );
            }
        } );
        getAllUsersFromKeycloak ().clear ();
        return utilisateurs;
    }

    // Add a user without assigning any role yet && return its Id ( String )
    private String addUserWithoutRole(User user) throws IOException {

        CredentialRepresentation credential = new CredentialRepresentation ();
        credential.setType ( CredentialRepresentation.PASSWORD );
        credential.setValue ( user.getPassword () );
        credential.setTemporary ( false );

        UserRepresentation userRep = new UserRepresentation ();
        userRep.setUsername ( user.getUserName () );
        userRep.setFirstName ( user.getFirstName () );
        userRep.setLastName ( user.getLastName () );
        userRep.setEmail ( user.getEmail () );
        userRep.setCredentials ( asList ( credential ) );
        userRep.setEnabled ( true );


        // Create the user
        Response result = kc.realm ( realm ).users ().create ( userRep );
        if (result.getStatus () != 201) {
            System.err.println ( "Couldn't create user." );
            System.exit ( 0 );
        }

         String userId = CreatedResponseUtil.getCreatedId ( result );
        return userId;
    }

    // call the addUserWithoutRole method and assign the USER role to this USER
    @RequestMapping (value = "/addUser", method = RequestMethod.POST)
    private String addUser(@ModelAttribute  User user) throws IOException {
        user.setType ( "USER" );

        String userId = addUserWithoutRole ( user );
        RealmResource realmResource = kc.realm(realm );
        UsersResource usersRessource = realmResource.users();
        UserResource userResource = usersRessource.get(userId);

        RoleRepresentation newUser = realmResource.roles()
                .get(user.getType ()).toRepresentation();

      // Assign realm role tester to user
        userResource.roles ().realmLevel ().add(Arrays.asList(newUser));

        return "redirect:/UsersList";
    }

    // call the addUserWithoutRole method and assign the USER role to this INSPECTOR
    @RequestMapping (value = "/addInspector", method = RequestMethod.POST)
    private String addInspector(@ModelAttribute  User inspector) throws IOException {
        inspector.setType ( "INSPECTOR" );

        String inspectorId = addUserWithoutRole ( inspector );
        RealmResource realmResource = kc.realm(realm );
        UsersResource usersRessource = realmResource.users();
        UserResource userResource = usersRessource.get(inspectorId);

        RoleRepresentation newUser = realmResource.roles()
                .get(inspector.getType ()).toRepresentation();

        // Assign realm role tester to user
        userResource.roles ().realmLevel ().add(Arrays.asList(newUser));

        return "redirect:/InspectorsList";
    }

    // Deleting a user and returning a view according to its role!
    @GetMapping(value = "/deleteUser/{id}")
    public String deleteUser(@PathVariable(name = "id") String id){
         isInspector = false;
        kc.realm(realm).users().get(id).remove();

        getInspectors ().forEach ( i->{
            if (i.getId ().equals ( id )){
                isInspector = true;
            }
        } );
        if (isInspector){
            return "redirect:/InspectorsList";
        }
        return "redirect:/UsersList";
    }

    // Getting user data as JSON, we use it in the JQuery to assign data to fields of the edit pop up form .
    @GetMapping(value = "/getUser")
    @ResponseBody
    public User findUser( String id){
        User userToEdit = new User();
         UserRepresentation u = kc.realm(realm).users().get(id).toRepresentation ();
         userToEdit.setId (u.getId ());
         userToEdit.setUserName (u.getUsername ());
         userToEdit.setFirstName ( u.getFirstName () );
         userToEdit.setLastName ( u.getLastName () );
         userToEdit.setEmail ( u.getEmail () );
         return userToEdit;
    }

    // Editting a user and returning a view according to its role!
   @RequestMapping (value = "/editUser", method = RequestMethod.POST)
    public String editUser(@ModelAttribute User userEdited){

        UsersResource usersResource = kc.realm(realm).users();
        UserResource userRes = usersResource.get(userEdited.getId ());

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(userEdited.getPassword ());
        credential.setTemporary(false);
        userRes.resetPassword ( credential );

        UserRepresentation user = usersResource.search(userRes.toRepresentation ().getUsername ()).get (0);
        user.setEmail(userEdited.getEmail ());
        user.setFirstName ( userEdited.getFirstName () );
        user.setLastName ( userEdited.getLastName () );

        usersResource.get(user.getId()).update(user);


        getInspectors ().forEach ( i->{
            if (i.getId ().equals ( userEdited.getId () )){
                isInspector = true;
            }
        } );
        if (isInspector){
            return "redirect:/InspectorsList";
        }
        return "redirect:/UsersList";
    }

}
