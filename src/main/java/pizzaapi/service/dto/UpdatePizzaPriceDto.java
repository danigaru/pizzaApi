package pizzaapi.service.dto;

import lombok.Data;

@Data
public class UpdatePizzaPriceDto {

    private Integer idPizza;
    private Double price;
}
