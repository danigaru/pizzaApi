package pizzaapi.service.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RandomOrderDto {

    private String idCustomer;
    private String method;
}
