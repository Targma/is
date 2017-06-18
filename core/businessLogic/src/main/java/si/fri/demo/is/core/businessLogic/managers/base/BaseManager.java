package si.fri.demo.is.core.businessLogic.managers.base;

import si.fri.demo.is.core.businessLogic.database.AuthorizationManager;
import si.fri.demo.is.core.businessLogic.database.Database;
import si.fri.demo.is.core.jpa.entities.base.BaseEntity;

public class BaseManager<T extends BaseEntity> {

    protected Database database;

    protected AuthorizationManager<T> authorizationManager;

    public BaseManager(Database database, AuthorizationManager<T> authorizationManager) {
        this.database = database;
        this.authorizationManager = authorizationManager;
    }

}
