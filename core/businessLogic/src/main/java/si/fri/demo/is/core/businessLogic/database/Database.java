package si.fri.demo.is.core.businessLogic.database;

import com.github.tfaga.lynx.beans.QueryParameters;
import com.github.tfaga.lynx.interfaces.CriteriaFilter;
import com.github.tfaga.lynx.utils.JPAUtils;
import org.jinq.jpa.JPAJinqStream;
import org.jinq.jpa.JinqJPAStreamProvider;
import si.fri.demo.is.core.businessLogic.authentication.base.AuthEntity;
import si.fri.demo.is.core.businessLogic.dto.Paging;
import si.fri.demo.is.core.businessLogic.exceptions.BusinessLogicTransactionException;
import si.fri.demo.is.core.jpa.entities.Customer;
import si.fri.demo.is.core.jpa.entities.User;
import si.fri.demo.is.core.jpa.entities.base.BaseEntity;
import si.fri.demo.is.core.jpa.entities.base.BaseEntityVersion;

import javax.persistence.EntityManager;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {
    private static final Logger LOG = Logger.getLogger(Database.class.getSimpleName());

    protected EntityManager em;
    protected JinqJPAStreamProvider source;

    public Database(EntityManager em) {
        this.em = em;
        this.source = new JinqJPAStreamProvider(em.getMetamodel());
    }

    private void validateEntity(BaseEntity baseEntity) throws BusinessLogicTransactionException, IllegalAccessException {
        String error = baseEntity.isValidDatabaseItem();
        if(error != null){
            throw new BusinessLogicTransactionException(Response.Status.BAD_REQUEST, error);
        }
    }

    /**
     * Example:
     * QueryParameters param = QueryParameters.query(uriInfo.getRequestUri().getQuery())
     * .defaultLimit(50).defaultOffset(0).build();
     *
     * @param c
     * @param param
     * @param <T>
     * @return
     * @throws BusinessLogicTransactionException
     */
    public <T extends BaseEntity> Paging<T> get(Class<T> c, QueryParameters param) throws BusinessLogicTransactionException {
        try{
            List<T> items = JPAUtils.queryEntities(em, c, param);
            Long count = JPAUtils.queryEntitiesCount(em, c, param);

            return new Paging<T>(items, count);
        } catch (Exception e){
            throw new BusinessLogicTransactionException(Response.Status.INTERNAL_SERVER_ERROR, "Could not process request.", e);
        }
    }

    public <T extends BaseEntity> Paging<T> get(Class<T> c, CriteriaFilter<T> customFilter) throws BusinessLogicTransactionException {
        try{
            List<T> items = JPAUtils.queryEntities(em, c, customFilter);
            Long count = JPAUtils.queryEntitiesCount(em, c, customFilter);

            return new Paging<T>(items, count);
        } catch (Exception e){
            throw new BusinessLogicTransactionException(Response.Status.INTERNAL_SERVER_ERROR, "Could not process custom filter.", e);
        }
    }

    public <T extends BaseEntity> T get(Class<T> c, Integer id) throws BusinessLogicTransactionException {
        try{
            T o = em.find(c, id);

            if(o == null){
                throw new BusinessLogicTransactionException(Response.Status.NOT_FOUND, "Could not find " + c.getClass().getSimpleName() + " with id: " + id);
            }

            return o;
        } catch (Exception e){
            throw new BusinessLogicTransactionException(Response.Status.INTERNAL_SERVER_ERROR, "Could not find " + c.getClass().getSimpleName() + " with id: " + id, e);
        }
    }

    public  <T extends BaseEntity> T create(T newEntity) throws BusinessLogicTransactionException {
        try {
            newEntity.update(newEntity, em);

            newEntity.prePersist();

            validateEntity(newEntity);

            em.persist(newEntity);

            return newEntity;
        } catch (Exception e){
            throw new BusinessLogicTransactionException(Response.Status.BAD_REQUEST, "Couldn't create object type " + newEntity.getClass().getSimpleName(), e);
        }
    }

    public <T extends BaseEntity> T update(T newEntity) throws BusinessLogicTransactionException {
        try {
            T dbEntity = em.find((Class<T>) newEntity.getClass(), newEntity.getId());

            if (dbEntity != null) {
                if(!dbEntity.isUpdateDifferent(newEntity)){
                    if(dbEntity.equals(newEntity)){
                        dbEntity.preUpdate();
                    } else {
                        logSkip();
                    }
                    return dbEntity;
                }

                dbEntity.update(newEntity, em);

                newEntity.preUpdate();

                validateEntity(dbEntity);

                em.merge(dbEntity);

                return dbEntity;
            } else {
                throw new BusinessLogicTransactionException(Response.Status.BAD_REQUEST, "Couldn't update object type " +
                        newEntity.getClass().getSimpleName() + " with id: " + newEntity.getId());
            }
        } catch (BusinessLogicTransactionException e){
            throw e;
        } catch (Exception e){
            throw new BusinessLogicTransactionException(Response.Status.INTERNAL_SERVER_ERROR, "Could not update entity generically", e);
        }
    }

    public <T extends BaseEntity> T patch(T newEntity) throws BusinessLogicTransactionException {
        try {
            T dbEntity = em.find((Class<T>) newEntity.getClass(), newEntity.getId());
            if (dbEntity != null) {
                if(!dbEntity.isPatchDifferent(newEntity)){
                    if(dbEntity.equals(newEntity)){
                        dbEntity.preUpdate();
                    } else {
                        logSkip();
                    }
                    return dbEntity;
                }

                dbEntity.patch(newEntity, em);

                newEntity.preUpdate();

                validateEntity(dbEntity);

                em.merge(dbEntity);

                return dbEntity;
            } else {
                throw new BusinessLogicTransactionException(Response.Status.BAD_REQUEST, "Couldn't patch object type " +
                        newEntity.getClass().getSimpleName() + " with id: " + newEntity.getId());
            }
        } catch (BusinessLogicTransactionException e){
            throw e;
        } catch (Exception e){
            throw new BusinessLogicTransactionException(Response.Status.INTERNAL_SERVER_ERROR, "Could not update entity generically", e);
        }
    }

    public <T extends BaseEntity> T delete(Class<T> c, Integer id) throws BusinessLogicTransactionException {
        try {
            T dbEntity = em.find(c, id);

            if (dbEntity == null) {
                throw new BusinessLogicTransactionException(Response.Status.BAD_REQUEST, "Couldn't delete object type " +
                        c.getSimpleName() + " with id: " + id);
            }

            dbEntity.setIsDeleted(true);
            em.merge(dbEntity);

            return dbEntity;
        } catch (BusinessLogicTransactionException e){
            throw e;
        } catch (Exception e){
            throw new BusinessLogicTransactionException(Response.Status.INTERNAL_SERVER_ERROR, "Could not update entity generically", e);
        }
    }

    public <T extends BaseEntity> T toggleIsDeleted(Class<T> c, Integer id) throws BusinessLogicTransactionException {
        try {
            T dbEntity = em.find(c, id);

            if (dbEntity == null) {
                throw new BusinessLogicTransactionException(Response.Status.BAD_REQUEST, "Couldn't delete object type " +
                        c.getSimpleName() + " with id: " + id);
            }

            dbEntity.setIsDeleted(!dbEntity.getIsDeleted());
            em.merge(dbEntity);

            return dbEntity;
        } catch (BusinessLogicTransactionException e){
            throw e;
        } catch (Exception e){
            throw new BusinessLogicTransactionException(Response.Status.INTERNAL_SERVER_ERROR, "Could not update entity generically", e);
        }
    }

    public <T extends BaseEntity> T permDelete(Class<T> c, Integer id) throws BusinessLogicTransactionException {
        try {
            T dbEntity = em.find(c, id);

            if (dbEntity == null) {
                throw new BusinessLogicTransactionException(Response.Status.BAD_REQUEST,"Couldn't delete object type " +
                        c.getSimpleName() + " with id: " + id);
            }

            em.remove(dbEntity);

            return dbEntity;
        } catch (BusinessLogicTransactionException e){
            throw e;
        } catch (Exception e){
            throw new BusinessLogicTransactionException(Response.Status.INTERNAL_SERVER_ERROR, "Could not update entity generically", e);
        }
    }

    public <T extends BaseEntityVersion> T createVersion(T newEntityVersion) throws BusinessLogicTransactionException {

        try {
            newEntityVersion.update(newEntityVersion, em);

            newEntityVersion.prePersist();

            validateEntity(newEntityVersion);

            em.persist(newEntityVersion);

            newEntityVersion.setOriginId(newEntityVersion.getId());
            em.merge(newEntityVersion);

            return newEntityVersion;
        } catch (BusinessLogicTransactionException e){
            throw e;
        } catch (Exception e){
            throw new BusinessLogicTransactionException(Response.Status.BAD_REQUEST, "Couldn't create object type " + newEntityVersion.getClass().getSimpleName(), e);
        }
    }

    public <T extends BaseEntityVersion> T updateVersion(int oldId, T newBaseEntityVersion) throws BusinessLogicTransactionException {
        try {
            T dbEntityVersion = em.find((Class<T>) newBaseEntityVersion.getClass(), oldId);

            if (dbEntityVersion == null) {
                throw new BusinessLogicTransactionException(Response.Status.BAD_REQUEST, "Couldn't update object type " +
                        dbEntityVersion.getClass().getSimpleName() + " with id: " + dbEntityVersion.getId());
            } else if(!dbEntityVersion.getIsLatest()){
                throw new BusinessLogicTransactionException(Response.Status.BAD_REQUEST, "Couldn't update object type " +
                        dbEntityVersion.getClass().getSimpleName() + " with id: " + dbEntityVersion.getId() + " is not latest");
            }

            if(!dbEntityVersion.isUpdateDifferent(newBaseEntityVersion)){
                logSkip();
                return dbEntityVersion;
            }

            dbEntityVersion.setIsLatest(false);
            em.merge(dbEntityVersion);

            T newEntityVersion = (T) dbEntityVersion.cloneObject();
            newEntityVersion.update(newBaseEntityVersion, em);
            newEntityVersion.prePersist(dbEntityVersion.getVersionOrder() + 1);

            validateEntity(newEntityVersion);

            em.persist(newEntityVersion);

            return newEntityVersion;
        } catch (BusinessLogicTransactionException e){
            throw e;
        } catch (Exception e){
            throw new BusinessLogicTransactionException(Response.Status.INTERNAL_SERVER_ERROR, "Could not update entity generically", e);
        }
    }

    public <T extends BaseEntityVersion> T patchVersion(int oldId, T newBaseEntityVersion) throws BusinessLogicTransactionException {
        try {
            T dbEntityVersion = em.find((Class<T>) newBaseEntityVersion.getClass(), oldId);

            if (dbEntityVersion == null) {
                throw new BusinessLogicTransactionException(Response.Status.BAD_REQUEST, "Couldn't patch object type " +
                        dbEntityVersion.getClass().getSimpleName() + " with id: " + dbEntityVersion.getId());
            } else if(!dbEntityVersion.getIsLatest()){
                throw new BusinessLogicTransactionException(Response.Status.BAD_REQUEST, "Couldn't update object type " +
                        dbEntityVersion.getClass().getSimpleName() + " with id: " + dbEntityVersion.getId() + " is not latest");
            }

            if(!dbEntityVersion.isPatchDifferent(newBaseEntityVersion)){
                logSkip();
                return dbEntityVersion;
            }

            dbEntityVersion.setIsLatest(false);
            em.merge(dbEntityVersion);

            T newEntityVersion = (T) dbEntityVersion.cloneObject();

            newEntityVersion.patch(newBaseEntityVersion, em);
            newEntityVersion.prePersist(newEntityVersion.getVersionOrder() + 1);

            validateEntity(newEntityVersion);

            em.persist(newEntityVersion);

            return newEntityVersion;
        } catch (Exception e){
            throw new BusinessLogicTransactionException(Response.Status.INTERNAL_SERVER_ERROR, "Could not update entity generically", e);
        }
    }

    public EntityManager getEntityManager() {
        return em;
    }

    public  <U extends BaseEntity> JPAJinqStream<U> getStream(Class<U> entity) {
        return source.streamAll(em, entity);
    }

    public void logSkip(){
        LOG.log(Level.INFO,"Skipped update, new entity had same values.");
    }

    public Customer getAuthorizedCustomer(AuthEntity authEntity) throws BusinessLogicTransactionException {
        final String authId = authEntity.getId();
        List<Customer> customerList = getStream(Customer.class)
                .where(e -> e.getAuthenticationId().equals(authId) && e.getIsLatest() && !e.getIsDeleted()).toList();

        if(customerList.isEmpty()){
            throw new BusinessLogicTransactionException(Response.Status.BAD_REQUEST,  "Customer account does not exist, you must activate it.");
        } else {
            return customerList.get(0);
        }
    }

    public User getAuthorizedUser(AuthEntity authEntity) throws BusinessLogicTransactionException {
        final String authId = authEntity.getId();
        List<User> userList = getStream(User.class)
                .where(e -> e.getAuthenticationId().equals(authId)).toList();

        if(userList.isEmpty()){
            throw new BusinessLogicTransactionException(Response.Status.BAD_REQUEST,  "User account does not exist, you must activate it.");
        } else {
            return userList.get(0);
        }
    }
}
