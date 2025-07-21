package uz.kruz.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import uz.kruz.domain.vo.OrderStatus;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@SuperBuilder
public class OrderDTO extends BaseDTO {
    private Integer userId;
    private Double totalAmount;
    private OrderStatus status;
}
