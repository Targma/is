package si.fri.demo.is.core.businessLogic.database;

import com.github.tfaga.lynx.beans.QueryParameters;
import com.github.tfaga.lynx.interfaces.CriteriaFilter;
import si.fri.demo.is.core.businessLogic.authentication.AuthEntity;
import si.fri.demo.is.core.businessLogic.exceptions.BusinessLogicTransactionException;
import si.fri.demo.is.core.jpa.entities.base.BaseEntity;

public abstract class AuthorizationManager<T extends BaseEntity> {

    protected AuthEntity authEntity;

    public AuthorizationManager(AuthEntity authEntity) {
        this.authEntity = authEntity;
    }

    public void setAuthorityFilter(QueryParameters queryParameters, Database database) throws BusinessLogicTransactionException {}
    public void setAuthorityCriteria(CriteriaFilter<T> criteriaAuthority, Database database) throws BusinessLogicTransactionException {}

    public void checkAuthority(T entity, Database database) throws BusinessLogicTransactionException {}
    public  void setEntityAuthority(T entityAuthority, Database database) throws BusinessLogicTransactionException {}
}
