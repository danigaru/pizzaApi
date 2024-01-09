package pizzaapi.persitence.projection;

import java.time.LocalDateTime;

public interface IOrderSummary {

    Integer getIdOrder();
    String getCustomerName();
    LocalDateTime getOrderDate();
    Double getOrderTotal();
    String getPizzaNames();
}
