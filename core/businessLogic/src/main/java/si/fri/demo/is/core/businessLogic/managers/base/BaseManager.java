package si.fri.demo.is.core.businessLogic.managers.base;

import si.fri.demo.is.core.businessLogic.database.Database;
import si.fri.demo.is.core.jpa.entities.base.BaseEntity;

public class BaseManager<T extends BaseEntity> {

    protected Database database;

    public BaseManager(Database database) {
        this.database = database;
    }

}
