package si.fri.demo.is.app.server.ejb.database;

import si.fri.demo.is.core.businessLogic.database.Database;
import si.fri.demo.is.core.businessLogic.database.DatabaseImpl;

public interface DatabaseServiceLocal extends DatabaseImpl {

    Database getDatabase();

}
