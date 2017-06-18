package si.fri.demo.is.app.server.rest.resources.utility;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import si.fri.demo.is.core.businessLogic.authentication.AuthEntity;

import java.security.Principal;

public class AuthUtility {

    public static AuthEntity getAuthorizedEntity(Principal principal) {
        KeycloakPrincipal<KeycloakSecurityContext> kcPrincipal = (KeycloakPrincipal) principal;
        AccessToken token = kcPrincipal.getKeycloakSecurityContext().getToken();

        AuthEntity authEntity = new AuthEntity();
        authEntity.setId(token.getSubject());
        authEntity.setName(token.getGivenName());
        authEntity.setSurname(token.getFamilyName());
        authEntity.setEmail(token.getEmail());
        authEntity.setRoles(token.getRealmAccess().getRoles());

        return authEntity;
    }

}
