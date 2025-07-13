package uz.kruz.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import uz.kruz.domain.vo.OrderStatus;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@SuperBuilder
public class OrderDTO extends BaseDTO {
    private Integer userId;
    private BigDecimal totalAmount;
    private OrderStatus status;

    @Override
    public String toString() {
        return "OrderDTO{" +
                "id=" + getId() +
                ", userId=" + userId +
                ", totalAmount=" + totalAmount +
                ", status=" + status +
                ", createdAt=" + getCreatedAt() +
                ", updatedAt=" + getUpdatedAt() +
                '}';
    }
}
