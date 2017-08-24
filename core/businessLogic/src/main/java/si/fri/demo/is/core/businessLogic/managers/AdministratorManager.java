package si.fri.demo.is.core.businessLogic.managers;

import si.fri.demo.is.core.businessLogic.authentication.AuthEntity;
import si.fri.demo.is.core.businessLogic.database.Database;
import si.fri.demo.is.core.businessLogic.exceptions.BusinessLogicTransactionException;
import si.fri.demo.is.core.businessLogic.managers.base.BaseManager;
import si.fri.demo.is.core.jpa.entities.Administrator;

import javax.ws.rs.core.Response;
import java.util.List;

import static si.fri.demo.is.core.businessLogic.authentication.AuthEntity.ROLE_ADMINISTRATOR;

public class AdministratorManager extends BaseManager<Administrator> {

    public AdministratorManager(Database database) {
        super(database, null);
    }

    public Administrator get(AuthEntity authEntity) throws BusinessLogicTransactionException {
        if(authEntity.isInRole(ROLE_ADMINISTRATOR)){
            final String authId = authEntity.getId();
            List<Administrator> administratorList = database.getStream(Administrator.class)
                    .where(e -> e.getAuthenticationId().equals(authId) && !e.getIsDeleted())
                    .toList();

            Administrator administrator;
            if(administratorList.isEmpty()){
                administrator = generate(authEntity);
                database.create(administrator, authorizationManager);
            } else {
                administrator = administratorList.get(0);
            }

            return administrator;
        } else {
            throw new BusinessLogicTransactionException(Response.Status.BAD_REQUEST, "Account is not of admin type.");
        }
    }

    private Administrator generate(AuthEntity authEntity) throws BusinessLogicTransactionException {
        Administrator administrator = new Administrator();
        administrator.setAuthenticationId(authEntity.getId());
        administrator.setEmail(authEntity.getEmail());
        administrator.setName(authEntity.getName());

        return administrator;
    }

}
