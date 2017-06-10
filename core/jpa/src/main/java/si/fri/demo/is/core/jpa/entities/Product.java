package si.fri.demo.is.core.jpa.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import si.fri.demo.is.core.jpa.entities.base.BaseEntityVersion;
import si.fri.demo.is.core.jpa.utility.Constants;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name="product")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class)
public class Product extends BaseEntityVersion<Product> {

    @Column(length = Constants.TITLE, nullable = false)
    private String title;

    @Column(length = Constants.DESCRIPTION)
    private String description;

    @Column(precision = Constants.PRECISION, scale = Constants.SCALE, nullable = false)
    private BigDecimal price;


    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private Set<ProductOnOrder> productOnOrders;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Set<ProductOnOrder> getProductOnOrders() {
        return productOnOrders;
    }

    public void setProductOnOrders(Set<ProductOnOrder> productOnOrders) {
        this.productOnOrders = productOnOrders;
    }
}
