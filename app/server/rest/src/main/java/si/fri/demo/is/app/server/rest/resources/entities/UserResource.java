package si.fri.demo.is.app.server.rest.resources.entities;

import si.fri.demo.is.app.server.ejb.interfaces.UserServiceLocal;
import si.fri.demo.is.app.server.rest.providers.configuration.PATCH;
import si.fri.demo.is.app.server.rest.resources.base.CrudResource;
import si.fri.demo.is.core.businessLogic.authentication.base.AuthEntity;
import si.fri.demo.is.core.businessLogic.exceptions.BusinessLogicTransactionException;
import si.fri.demo.is.core.jpa.entities.User;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.security.Principal;

@Path("User")
@RequestScoped
public class UserResource extends CrudResource<User> {

    @EJB
    private UserServiceLocal userService;

    public UserResource() {
        super(User.class);
    }

    @RolesAllowed(ROLE_ADMINISTRATOR)
    @GET
    @Override
    public Response getList() throws BusinessLogicTransactionException {
        return super.getList();
    }

    @RolesAllowed(ROLE_ADMINISTRATOR)
    @GET
    @Path("{id}")
    @Override
    public Response get(@PathParam("id") Integer id) throws BusinessLogicTransactionException {
        return super.get(id);
    }

    @RolesAllowed(ROLE_ADMINISTRATOR)
    @PUT
    @Path("{id}")
    @Override
    public Response update(Integer id, User newObject) throws BusinessLogicTransactionException {
        return super.update(id, newObject);
    }

    @RolesAllowed(ROLE_ADMINISTRATOR)
    @PATCH
    @Path("{id}")
    @Override
    public Response patch(Integer id, User newObject) throws BusinessLogicTransactionException {
        return super.patch(id, newObject);
    }

    @RolesAllowed(ROLE_ADMINISTRATOR)
    @POST
    @Override
    public Response create(User object) throws BusinessLogicTransactionException {
        return super.create(object);
    }

    @RolesAllowed(ROLE_ADMINISTRATOR)
    @DELETE
    @Path("{id}")
    @Override
    public Response delete(Integer id) throws BusinessLogicTransactionException {
        return super.delete(id);
    }

    @RolesAllowed(ROLE_ADMINISTRATOR)
    @PUT
    @Path("{id}/toggleIsDeleted")
    @Override
    public Response toggleIsDeleted(Integer id) throws BusinessLogicTransactionException {
        return super.toggleIsDeleted(id);
    }

    @RolesAllowed(ROLE_ADMINISTRATOR)
    @GET
    @Path("login")
    public Response loginUserInfo() throws BusinessLogicTransactionException {
        Principal principal = sc.getUserPrincipal();
        AuthEntity authEntity = authProvider.generateAuthEntity(principal);
        User user = userService.get(authEntity);
        return Response.ok(user).build();
    }

    @Override
    protected void authorizationValidation(Integer id) throws BusinessLogicTransactionException {
        AuthEntity authEntity = authProvider.generateAuthEntity(sc.getUserPrincipal());
        User user = databaseService.getDatabase().getAuthorizedUser(authEntity);
        if(user.getId() != id){
            throw new BusinessLogicTransactionException(Response.Status.FORBIDDEN, "User does not have permission.");
        }
    }
}
