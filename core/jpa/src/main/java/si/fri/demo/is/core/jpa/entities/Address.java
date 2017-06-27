package si.fri.demo.is.core.jpa.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import si.fri.demo.is.core.jpa.entities.base.BaseEntity;
import si.fri.demo.is.core.jpa.entities.base.BaseEntityVersion;
import si.fri.demo.is.core.jpa.utility.Constants;

import javax.persistence.*;

@Entity
@Table(name="address")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class)
public class Address extends BaseEntityVersion<Address> {

    @Column(length = Constants.DEF_STRING_LEN)
    private String city;

    @Column(length = Constants.DEF_CODE_LEN)
    private String code;

    @Column(length = Constants.DEF_CODE_LEN)
    private String street;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;


    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

}
