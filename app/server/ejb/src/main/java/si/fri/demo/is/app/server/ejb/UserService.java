package si.fri.demo.is.app.server.ejb;

import si.fri.demo.is.app.server.ejb.base.BaseService;
import si.fri.demo.is.app.server.ejb.interfaces.UserServiceLocal;
import si.fri.demo.is.core.businessLogic.authentication.base.AuthEntity;
import si.fri.demo.is.core.businessLogic.exceptions.BusinessLogicTransactionException;
import si.fri.demo.is.core.businessLogic.managers.UserManager;
import si.fri.demo.is.core.jpa.entities.User;

import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local(UserServiceLocal.class)
public class UserService extends BaseService implements UserServiceLocal {

    protected UserManager userManager;

    @PostConstruct
    protected void init() {
        userManager = new UserManager(databaseService.getDatabase());
    }

    @Override
    public User get(AuthEntity authEntity) throws BusinessLogicTransactionException {
        return userManager.get(authEntity);
    }
}
