package si.fri.demo.is.core.businessLogic.database;

import si.fri.demo.is.core.businessLogic.exceptions.BusinessLogicTransactionException;
import si.fri.demo.is.core.jpa.entities.base.BaseEntity;

public abstract class ValidationManager<T extends BaseEntity> {

    public void baseValidate(T entity, Database database) throws BusinessLogicTransactionException { }

    public void createValidate(T entity, Database database) throws BusinessLogicTransactionException { baseValidate(entity, database); }
    public void updateValidate(T entity, Database database) throws BusinessLogicTransactionException { baseValidate(entity, database); }
    public void patchValidate(T entity, Database database) throws BusinessLogicTransactionException { baseValidate(entity, database); }

    public void deleteValidate(T entity, Database database) throws BusinessLogicTransactionException { }
    public void toggleDeleteValidate(T entity, Database database) throws BusinessLogicTransactionException { }
    public void permDeleteValidate(T entity, Database database) throws BusinessLogicTransactionException { }

}
