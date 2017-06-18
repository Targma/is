package si.fri.demo.is.core.businessLogic.managers;

import si.fri.demo.is.core.businessLogic.authentication.AuthEntity;
import si.fri.demo.is.core.businessLogic.database.Database;
import si.fri.demo.is.core.businessLogic.exceptions.BusinessLogicTransactionException;
import si.fri.demo.is.core.businessLogic.managers.base.BaseManager;
import si.fri.demo.is.core.jpa.entities.User;

import javax.ws.rs.core.Response;
import java.util.List;

import static si.fri.demo.is.core.businessLogic.authentication.AuthEntity.ROLE_ADMINISTRATOR;

public class UserManager extends BaseManager<User> {

    public UserManager(Database database) {
        super(database, null);
    }

    public User get(AuthEntity authEntity) throws BusinessLogicTransactionException {
        if(authEntity.isInRole(ROLE_ADMINISTRATOR)){
            final String authId = authEntity.getId();
            List<User> userList = database.getStream(User.class)
                    .where(e -> e.getAuthenticationId().equals(authId) && !e.getIsDeleted())
                    .toList();

            User user;
            if(userList.isEmpty()){
                user = generate(authEntity);
                database.create(user, authorizationManager);
            } else {
                user = userList.get(0);
            }

            return user;
        } else {
            throw new BusinessLogicTransactionException(Response.Status.BAD_REQUEST, "Account is not of admin type.");
        }
    }

    private User generate(AuthEntity authEntity) throws BusinessLogicTransactionException {
        User user = new User();
        user.setAuthenticationId(authEntity.getId());
        user.setEmail(authEntity.getEmail());
        user.setName(authEntity.getName());

        return user;
    }

}
