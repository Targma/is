package si.fri.demo.is.app.server.ejb.database;

import com.github.tfaga.lynx.beans.QueryParameters;
import com.github.tfaga.lynx.interfaces.CriteriaFilter;
import org.jinq.jpa.JPAJinqStream;
import si.fri.demo.is.core.businessLogic.authentication.AuthEntity;
import si.fri.demo.is.core.businessLogic.database.AuthorizationManager;
import si.fri.demo.is.core.businessLogic.database.Database;
import si.fri.demo.is.core.businessLogic.database.ValidationManager;
import si.fri.demo.is.core.businessLogic.dto.Paging;
import si.fri.demo.is.core.businessLogic.exceptions.BusinessLogicTransactionException;
import si.fri.demo.is.core.jpa.entities.Customer;
import si.fri.demo.is.core.jpa.entities.Administrator;
import si.fri.demo.is.core.jpa.entities.base.BaseEntity;
import si.fri.demo.is.core.jpa.entities.base.BaseEntityVersion;

import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.logging.Logger;

@PermitAll
@Stateless
@Local(DatabaseServiceLocal.class)
public class DatabaseService implements DatabaseServiceLocal {

    private static final Logger LOG = Logger.getLogger(DatabaseService.class.getSimpleName());

    @PersistenceContext(unitName = "is-jpa")
    private EntityManager em;

    private Database database;

    @PostConstruct
    private void init(){
        this.database = new Database(em);
    }

    @Override
    public <T extends BaseEntity> Paging<T> getList(Class<T> c, QueryParameters param, AuthorizationManager<T> authorizationManager) throws BusinessLogicTransactionException {
        return database.getList(c, param, authorizationManager);
    }

    @Override
    public <T extends BaseEntity> Paging<T> getList(Class<T> c, CriteriaFilter<T> customFilter, AuthorizationManager<T> authorizationManager) throws BusinessLogicTransactionException {
        return database.getList(c, customFilter, authorizationManager);
    }

    @Override
    public <T extends BaseEntity> T get(Class<T> c, Integer id, AuthorizationManager<T> authorizationManager) throws BusinessLogicTransactionException {
        return database.get(c, id, authorizationManager);
    }

    @Override
    public <T extends BaseEntity> T create(T newEntity, AuthorizationManager<T> authorizationManager, ValidationManager<T> validationManager) throws BusinessLogicTransactionException {
        return database.create(newEntity, authorizationManager, validationManager);
    }

    @Override
    public <T extends BaseEntity> T update(T newEntity, AuthorizationManager<T> authorizationManager, ValidationManager<T> validationManager) throws BusinessLogicTransactionException {
        return database.update(newEntity, authorizationManager, validationManager);
    }

    @Override
    public <T extends BaseEntity> T patch(T newEntity, AuthorizationManager<T> authorizationManager, ValidationManager<T> validationManager) throws BusinessLogicTransactionException {
        return database.patch(newEntity, authorizationManager, validationManager);
    }

    @Override
    public <T extends BaseEntity> T delete(Class<T> c, Integer id, AuthorizationManager<T> authorizationManager, ValidationManager<T> validationManager) throws BusinessLogicTransactionException {
        return database.delete(c, id, authorizationManager, validationManager);
    }

    @Override
    public <T extends BaseEntity> T toggleIsDeleted(Class<T> c, Integer id, AuthorizationManager<T> authorizationManager, ValidationManager<T> validationManager) throws BusinessLogicTransactionException {
        return database.toggleIsDeleted(c, id, authorizationManager, validationManager);
    }

    @Override
    public <T extends BaseEntity> T permDelete(Class<T> c, Integer id, AuthorizationManager<T> authorizationManager, ValidationManager<T> validationManager) throws BusinessLogicTransactionException {
        return database.permDelete(c, id, authorizationManager, validationManager);
    }

    @Override
    public <T extends BaseEntityVersion> T createVersion(T newEntityVersion, AuthorizationManager<T> authorizationManager, ValidationManager<T> validationManager) throws BusinessLogicTransactionException {
        return database.createVersion(newEntityVersion, authorizationManager, validationManager);
    }

    @Override
    public <T extends BaseEntityVersion> T updateVersion(int oldId, T newBaseEntityVersion, AuthorizationManager<T> authorizationManager, ValidationManager<T> validationManager) throws BusinessLogicTransactionException {
        return database.updateVersion(oldId, newBaseEntityVersion, authorizationManager, validationManager);
    }

    @Override
    public <T extends BaseEntityVersion> T patchVersion(int oldId, T newBaseEntityVersion, AuthorizationManager<T> authorizationManager, ValidationManager<T> validationManager) throws BusinessLogicTransactionException {
        return database.patchVersion(oldId, newBaseEntityVersion, authorizationManager, validationManager);
    }

    @Override
    public EntityManager getEntityManager() {
        return database.getEntityManager();
    }

    @Override
    public <U extends BaseEntity> JPAJinqStream<U> getStream(Class<U> entity) {
        return null;
    }

    @Override
    public Customer getAuthorizedCustomer(AuthEntity authEntity) throws BusinessLogicTransactionException {
        return database.getAuthorizedCustomer(authEntity);
    }

    @Override
    public Administrator getAuthorizedUser(AuthEntity authEntity) throws BusinessLogicTransactionException {
        return database.getAuthorizedUser(authEntity);
    }

    @Override
    public Database getDatabase() {
        return database;
    }
}
