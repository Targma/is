package si.fri.demo.is.core.businessLogic.calculation;

import si.fri.demo.is.core.jpa.entities.Order;
import si.fri.demo.is.core.jpa.entities.Product;
import si.fri.demo.is.core.jpa.entities.ProductOnOrder;

import java.math.BigDecimal;

public class Calculation {

    public static final BigDecimal BD_0 = new BigDecimal("0");
    public static final BigDecimal BD_1 = new BigDecimal("1");
    public static final BigDecimal BD_100 = new BigDecimal("100");

    public static BigDecimal getTotalAmount(Order order){

        BigDecimal totalAmount = BD_0;

        for(ProductOnOrder item : order.getProductOnOrders()){
            totalAmount = totalAmount.add(getProductOnOrderPrice(item));
        }

        return totalAmount;
    }

    public static BigDecimal getProductOnOrderPrice(ProductOnOrder productOnOrder){

        BigDecimal price = getProductPrice(productOnOrder.getProduct());
        BigDecimal discount = productOnOrder.getDiscount();
        BigDecimal quantity = productOnOrder.getQuantity();

        BigDecimal totalPrice = price.multiply(quantity);
        BigDecimal priceWithDiscount = getPriceWithDiscount(totalPrice, discount);

        return setScale2(priceWithDiscount);

    }

    public static BigDecimal getProductPrice(Product product){
        return product.getPrice();
    }

    public static BigDecimal getPriceWithDiscount(BigDecimal price, BigDecimal discount){
        return setScale2(price.multiply(BD_1.subtract(getPercentageRatio(discount))));
    }

    public static BigDecimal setScale2(BigDecimal bigDecimal){
        return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal getPercentageRatio(BigDecimal bigDecimal){
        return bigDecimal.divide(BD_100, 4, BigDecimal.ROUND_HALF_UP);
    }
}
