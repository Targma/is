package si.fri.demo.is.core.restComponents.managers;

import si.fri.demo.is.core.businessLogic.database.Database;
import si.fri.demo.is.core.businessLogic.database.ValidationManager;
import si.fri.demo.is.core.businessLogic.exceptions.BusinessLogicTransactionException;
import si.fri.demo.is.core.jpa.entities.base.BaseEntity;
import si.fri.demo.is.core.restComponents.exceptions.ETagException;

import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

public abstract class ETagValidationManager<T extends BaseEntity> extends ValidationManager<T> {

    @Override
    public void updateValidate(T entity, Database database) throws BusinessLogicTransactionException {
        checkETag(entity);
    }

    @Override
    public void patchValidate(T entity, Database database) throws BusinessLogicTransactionException {
        checkETag(entity);
    }

    @Override
    public void deleteValidate(T entity, Database database) throws BusinessLogicTransactionException {
        checkETag(entity);
    }

    @Override
    public void toggleDeleteValidate(T entity, Database database) throws BusinessLogicTransactionException {
        checkETag(entity);
    }

    private void checkETag(T entity) throws BusinessLogicTransactionException {
        Response.ResponseBuilder rb = getRequest().evaluatePreconditions(entity.getEntityTag());
        if(rb != null) {
            throw new ETagException(Response.Status.PRECONDITION_FAILED, null, rb);
        }
    }

    protected abstract Request getRequest();

}
