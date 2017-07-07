package si.fri.demo.is.core.businessLogic.database;

import com.github.tfaga.lynx.beans.QueryParameters;
import com.github.tfaga.lynx.interfaces.CriteriaFilter;
import org.jinq.jpa.JPAJinqStream;
import si.fri.demo.is.core.businessLogic.authentication.AuthEntity;
import si.fri.demo.is.core.businessLogic.dto.Paging;
import si.fri.demo.is.core.businessLogic.exceptions.BusinessLogicTransactionException;
import si.fri.demo.is.core.jpa.entities.Customer;
import si.fri.demo.is.core.jpa.entities.User;
import si.fri.demo.is.core.jpa.entities.base.BaseEntity;
import si.fri.demo.is.core.jpa.entities.base.BaseEntityVersion;

import javax.persistence.EntityManager;

public interface DatabaseImpl {

    <T extends BaseEntity> Paging<T> getList(Class<T> c, QueryParameters param, AuthorizationManager<T> authorizationManager) throws BusinessLogicTransactionException;
    <T extends BaseEntity> Paging<T> getList(Class<T> c, CriteriaFilter<T> customFilter, AuthorizationManager<T> authorizationManager) throws BusinessLogicTransactionException;
    <T extends BaseEntity> T get(Class<T> c, Integer id, AuthorizationManager<T> authorizationManager) throws BusinessLogicTransactionException;

    <T extends BaseEntity> T create(T newEntity, AuthorizationManager<T> authorizationManager, ValidationManager<T> validationManager) throws BusinessLogicTransactionException;
    <T extends BaseEntity> T update(T newEntity, AuthorizationManager<T> authorizationManager, ValidationManager<T> validationManager) throws BusinessLogicTransactionException;
    <T extends BaseEntity> T patch(T newEntity, AuthorizationManager<T> authorizationManager, ValidationManager<T> validationManager) throws BusinessLogicTransactionException;
    <T extends BaseEntity> T delete(Class<T> c, Integer id, AuthorizationManager<T> authorizationManager, ValidationManager<T> validationManager) throws BusinessLogicTransactionException;
    <T extends BaseEntity> T toggleIsDeleted(Class<T> c, Integer id, AuthorizationManager<T> authorizationManager, ValidationManager<T> validationManager) throws BusinessLogicTransactionException;
    <T extends BaseEntity> T permDelete(Class<T> c, Integer id, AuthorizationManager<T> authorizationManager, ValidationManager<T> validationManager) throws BusinessLogicTransactionException;
    <T extends BaseEntityVersion> T createVersion(T newEntityVersion, AuthorizationManager<T> authorizationManager, ValidationManager<T> validationManager) throws BusinessLogicTransactionException;
    <T extends BaseEntityVersion> T updateVersion(int oldId, T newBaseEntityVersion, AuthorizationManager<T> authorizationManager, ValidationManager<T> validationManager) throws BusinessLogicTransactionException;
    <T extends BaseEntityVersion> T patchVersion(int oldId, T newBaseEntityVersion, AuthorizationManager<T> authorizationManager, ValidationManager<T> validationManager) throws BusinessLogicTransactionException;

    EntityManager getEntityManager();
    <U extends BaseEntity> JPAJinqStream<U> getStream(Class<U> entity);

    Customer getAuthorizedCustomer(AuthEntity authEntity) throws BusinessLogicTransactionException;
    User getAuthorizedUser(AuthEntity authEntity) throws BusinessLogicTransactionException;

}
