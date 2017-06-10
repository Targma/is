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

import javax.persistence.EntityManager;

public interface DatabaseServiceLocal {

    <T extends BaseEntity> Paging<T> get(Class<T> c, QueryParameters param) throws BusinessLogicTransactionException;
    <T extends BaseEntity> Paging<T> get(Class<T> c, CriteriaFilter<T> customFilter) throws BusinessLogicTransactionException;
    <T extends BaseEntity> T get(Class<T> c, Integer id) throws BusinessLogicTransactionException;
    <T extends BaseEntity> T create(T newEntity) throws BusinessLogicTransactionException;

    BaseEntity update(BaseEntity newEntity) throws BusinessLogicTransactionException;
    BaseEntity patch(BaseEntity newEntity) throws BusinessLogicTransactionException;
    BaseEntity delete(Class<? extends BaseEntity> c, Integer id) throws BusinessLogicTransactionException;
    BaseEntity toggleIsDeleted(Class<? extends BaseEntity> c, Integer id) throws BusinessLogicTransactionException;
    BaseEntity permDelete(Class<? extends BaseEntity> c, Integer id) throws BusinessLogicTransactionException;
    BaseEntityVersion createVersion(BaseEntityVersion newEntityVersion) throws BusinessLogicTransactionException;
    BaseEntityVersion updateVersion(int oldId, BaseEntityVersion newBaseEntityVersion) throws BusinessLogicTransactionException;
    BaseEntityVersion patchVersion(int oldId, BaseEntityVersion newBaseEntityVersion) throws BusinessLogicTransactionException;

    EntityManager getEntityManager();
    <U> JPAJinqStream<U> getStream(Class<U> entity);

    Customer getAuthorizedCustomer(AuthEntity authEntity) throws BusinessLogicTransactionException;
    User getAuthorizedUser(AuthEntity authEntity) throws BusinessLogicTransactionException;

    /**
     * Only usable in transaction session (EJB)
     * @return
     */
    Database getDatabase();
}
