package si.fri.demo.is.core.businessLogic.authentication;

import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import si.fri.demo.is.core.businessLogic.authentication.base.AuthEntity;

public class KeycloakAuthEntity extends AuthEntity {

    public KeycloakAuthEntity(KeycloakSecurityContext keycloakSecurityContext) {
        AccessToken token =keycloakSecurityContext.getToken();

         id = token.getId();
         name = token.getName();
         surname = token.getFamilyName();

    }
}
