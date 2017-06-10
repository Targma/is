package si.fri.demo.is.core.businessLogic.managers;

import si.fri.demo.is.core.businessLogic.authentication.base.AuthEntity;
import si.fri.demo.is.core.businessLogic.database.Database;
import si.fri.demo.is.core.businessLogic.exceptions.BusinessLogicTransactionException;
import si.fri.demo.is.core.businessLogic.managers.base.BaseManager;
import si.fri.demo.is.core.jpa.entities.User;

import javax.ws.rs.core.Response;
import java.util.List;

public class UserManager extends BaseManager<User> {

    public UserManager(Database database) {
        super(database);
    }

    public User get(AuthEntity authEntity) throws BusinessLogicTransactionException {
        if(authEntity.getAccountType() == AuthEntity.AccountType.ADMIN){
            final String authId = authEntity.getId();
            List<User> userList = database.getStream(User.class)
                    .where(e -> e.getAuthenticationId().equals(authId))
                    .toList();

            User user;
            if(userList.isEmpty()){
                user = generate(authEntity);
                database.create(user);
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

        database.create(user);

        return user;
    }

}
