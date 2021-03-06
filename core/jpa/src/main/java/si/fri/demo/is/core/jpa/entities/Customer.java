package si.fri.demo.is.core.jpa.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import si.fri.demo.is.core.jpa.entities.base.BaseEntityVersion;
import si.fri.demo.is.core.jpa.utility.Constants;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.util.Set;

@Entity
@Table(name="customer")
@Cacheable(true)
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class)
public class Customer extends BaseEntityVersion<Customer> {

    @Column(length = Constants.DEF_STRING_LEN, nullable = false)
    private String email;

    @Column(length = Constants.DEF_STRING_LEN)
    private String name;

    @Column(length = Constants.DEF_STRING_LEN)
    private String surname;

    @Column(length = Constants.DEF_STRING_LEN)
    private String telephoneNumber;

    @Column(length = Constants.DEF_STRING_LEN, nullable = false)
    private String authenticationId;


    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private Set<Address> addresses;


    @JsonIgnore
    protected boolean baseSkip(Field field){
        boolean skip = super.baseSkip(field);
        if(skip){
            return skip;
        } else {
            switch (field.getName()) {
                case "email":
                case "authenticationId":
                    return true;
                default:
                    return false;
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getAuthenticationId() {
        return authenticationId;
    }

    public void setAuthenticationId(String keycloakId) {
        this.authenticationId = keycloakId;
    }
}
