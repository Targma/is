package si.fri.demo.is.app.server.ejb.base;

import si.fri.demo.is.app.server.ejb.database.DatabaseServiceLocal;

import javax.ejb.EJB;

public abstract class BaseService {

    @EJB
    protected DatabaseServiceLocal databaseService;

}
