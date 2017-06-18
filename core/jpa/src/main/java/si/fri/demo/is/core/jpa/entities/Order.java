package si.fri.demo.is.core.jpa.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import si.fri.demo.is.core.jpa.entities.base.BaseEntity;
import si.fri.demo.is.core.jpa.utility.Constants;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name="\"order\"")
@Cacheable(true)
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class)
public class Order extends BaseEntity<Order> {

    @Column(precision = Constants.PRECISION, scale = Constants.SCALE, nullable = false)
    private BigDecimal price;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    private Set<ProductOnOrder> productOnOrders;


    public Set<ProductOnOrder> getProductOnOrders() {
        return productOnOrders;
    }

    public void setProductOnOrders(Set<ProductOnOrder> productOnOrders) {
        this.productOnOrders = productOnOrders;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
