package si.fri.demo.is.app.server.ejb;

import si.fri.demo.is.app.server.ejb.base.BaseService;
import si.fri.demo.is.app.server.ejb.interfaces.AdministratorServiceLocal;
import si.fri.demo.is.core.businessLogic.authentication.AuthEntity;
import si.fri.demo.is.core.businessLogic.exceptions.BusinessLogicTransactionException;
import si.fri.demo.is.core.businessLogic.managers.AdministratorManager;
import si.fri.demo.is.core.jpa.entities.Administrator;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import javax.ejb.Stateless;

@RolesAllowed(AuthEntity.ROLE_ADMINISTRATOR)
@Stateless
@Local(AdministratorServiceLocal.class)
public class AdministratorService extends BaseService implements AdministratorServiceLocal {

    protected AdministratorManager administratorManager;

    @PostConstruct
    protected void init() {
        administratorManager = new AdministratorManager(databaseService.getDatabase());
    }

    @Override
    public Administrator get(AuthEntity authEntity) throws BusinessLogicTransactionException {
        return administratorManager.get(authEntity);
    }
}
