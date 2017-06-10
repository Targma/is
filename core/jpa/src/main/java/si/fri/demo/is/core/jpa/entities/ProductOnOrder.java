package si.fri.demo.is.core.jpa.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import si.fri.demo.is.core.jpa.entities.base.BaseEntity;
import si.fri.demo.is.core.jpa.utility.Constants;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="product_on_order")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class)
public class ProductOnOrder extends BaseEntity<ProductOnOrder> {

    @Column(precision = Constants.PRECISION, scale = Constants.SCALE, nullable = false)
    protected BigDecimal quantity;

    @Column(precision = Constants.PRECISION, scale = Constants.SCALE, nullable = false)
    protected BigDecimal discount;

    @Column(name = "order_number", nullable = false)
    protected Short orderNumber;


    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;


    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public Short getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Short orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
