package uz.kruz.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@SuperBuilder
public class OrderItemDTO extends BaseDTO {
    private Integer orderId;
    private Integer bookId;
    private Integer quantity;
    private Double price;
}
