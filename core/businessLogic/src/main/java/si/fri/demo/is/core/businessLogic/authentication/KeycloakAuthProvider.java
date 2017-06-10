package si.fri.demo.is.core.businessLogic.authentication;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import si.fri.demo.is.core.businessLogic.authentication.base.AuthEntity;
import si.fri.demo.is.core.businessLogic.authentication.base.AuthProvider;

import java.security.Principal;

public class KeycloakAuthProvider implements AuthProvider {

    @Override
    public AuthEntity generateAuthEntity(Principal principal) {
        KeycloakPrincipal<KeycloakSecurityContext> kp = (KeycloakPrincipal<KeycloakSecurityContext>)  principal;
        KeycloakSecurityContext keycloakSecurityContext = kp.getKeycloakSecurityContext();
        return new KeycloakAuthEntity(keycloakSecurityContext);

    }
}
