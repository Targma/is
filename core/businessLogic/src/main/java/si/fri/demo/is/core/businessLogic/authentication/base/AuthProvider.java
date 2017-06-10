package si.fri.demo.is.core.businessLogic.authentication.base;

import java.security.Principal;

public interface AuthProvider {

    AuthEntity generateAuthEntity(Principal principal);

}
