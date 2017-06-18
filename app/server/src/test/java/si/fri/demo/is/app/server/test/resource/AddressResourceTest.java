package si.fri.demo.is.app.server.test.resource;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.runner.RunWith;
import si.fri.demo.is.api.resource.base.ISCrudResource;
import si.fri.demo.is.app.server.test.resource.base.CrudResourceTest;
import si.fri.demo.is.core.jpa.entities.Address;

@RunWith(Arquillian.class)
public class AddressResourceTest extends CrudResourceTest<Address, ISCrudResource<Address>> {

    @Override
    protected ISCrudResource<Address> getResource() {
        return api.address;
    }

    public static Address buildAddress(){
        Address address = new Address();
        address.setCity("Ljubljana");
        address.setCode("1000");
        return address;
    }

    @Override
    protected Address buildCreateEntity() {
        return buildAddress();
    }

    @Override
    protected Address buildPutEntity(Address dbAddress) {
        Address address = (Address) dbAddress.cloneObject();
        address.setCity(dbAddress.getCity() + " PUT");
        return address;
    }

    @Override
    protected Address buildPatchEntity(Address dbAddress) {
        Address address = new Address();
        address.setId(dbAddress.getId());
        address.setCity(dbAddress.getCity() + " PATCH");
        return address;
    }

    @Override
    protected void setEntity(Address source, Address dest) {
        super.setEntity(source, dest);
        dest.setCustomer(source.getCustomer());
    }
}
