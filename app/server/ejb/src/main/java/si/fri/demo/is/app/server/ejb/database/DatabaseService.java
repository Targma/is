package si.fri.demo.is.app.server.ejb.database;

import com.github.tfaga.lynx.beans.QueryParameters;
import com.github.tfaga.lynx.interfaces.CriteriaFilter;
import org.jinq.jpa.JPAJinqStream;
import si.fri.demo.is.core.businessLogic.authentication.base.AuthEntity;
import si.fri.demo.is.core.businessLogic.database.Database;
import si.fri.demo.is.core.businessLogic.dto.Paging;
import si.fri.demo.is.core.businessLogic.exceptions.BusinessLogicTransactionException;
import si.fri.demo.is.core.jpa.entities.Customer;
import si.fri.demo.is.core.jpa.entities.User;
import si.fri.demo.is.core.jpa.entities.base.BaseEntity;
import si.fri.demo.is.core.jpa.entities.base.BaseEntityVersion;

import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import javax.ejb.Local;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.logging.Logger;

@PermitAll
@Stateful
@Local(DatabaseServiceLocal.class)
public class DatabaseService implements DatabaseServiceLocal {

    private static final Logger LOG = Logger.getLogger(DatabaseService.class.getSimpleName());

    @PersistenceContext(unitName = "is-jpa")
    private EntityManager em;

    private Database database;

    //@TransactionAttribute(value = TransactionAttributeType.REQUIRES_NEW)
    @PostConstruct
    private void init(){
        this.database = new Database(em);
    }


    public <T extends BaseEntity> Paging<T> get(Class<T> c, QueryParameters param) throws BusinessLogicTransactionException {
        return database.get(c, param);
    }

    @Override
    public <T extends BaseEntity> Paging<T> get(Class<T> c, CriteriaFilter<T> customFilter) throws BusinessLogicTransactionException {
        return database.get(c, customFilter);
    }

    @Override
    public <T extends BaseEntity> T get(Class<T> c, Integer id) throws BusinessLogicTransactionException {
        return database.get(c, id);
    }

    @Override
    public <T extends BaseEntity> T create(T newEntity) throws BusinessLogicTransactionException {
        return database.create(newEntity);
    }

    @Override
    public BaseEntity update(BaseEntity newEntity) throws BusinessLogicTransactionException {
        return database.update(newEntity);
    }

    @Override
    public BaseEntity patch(BaseEntity newEntity) throws BusinessLogicTransactionException {
        return database.patch(newEntity);
    }

    @Override
    public BaseEntity delete(Class<? extends BaseEntity> c, Integer id) throws BusinessLogicTransactionException {
        return database.delete(c, id);
    }

    @Override
    public BaseEntity toggleIsDeleted(Class<? extends BaseEntity> c, Integer id) throws BusinessLogicTransactionException {
        return database.toggleIsDeleted(c, id);
    }

    @Override
    public BaseEntity permDelete(Class<? extends BaseEntity> c, Integer id) throws BusinessLogicTransactionException {
        return database.permDelete(c, id);
    }

    @Override
    public BaseEntityVersion createVersion(BaseEntityVersion newEntityVersion) throws BusinessLogicTransactionException {
        return database.createVersion(newEntityVersion);
    }

    @Override
    public BaseEntityVersion updateVersion(int oldId, BaseEntityVersion newBaseEntityVersion) throws BusinessLogicTransactionException {
        return database.updateVersion(oldId, newBaseEntityVersion);
    }

    @Override
    public BaseEntityVersion patchVersion(int oldId, BaseEntityVersion newBaseEntityVersion) throws BusinessLogicTransactionException {
        return database.patchVersion(oldId, newBaseEntityVersion);
    }

    @Override
    public EntityManager getEntityManager() {
        return database.getEntityManager();
    }

    @Override
    public <U> JPAJinqStream<U> getStream(Class<U> entity) {
        return getStream(entity);
    }

    @Override
    public Customer getAuthorizedCustomer(AuthEntity authEntity) throws BusinessLogicTransactionException {
        return database.getAuthorizedCustomer(authEntity);
    }

    @Override
    public User getAuthorizedUser(AuthEntity authEntity) throws BusinessLogicTransactionException {
        return database.getAuthorizedUser(authEntity);
    }

    public Database getDatabase() {
        return database;
    }
}
