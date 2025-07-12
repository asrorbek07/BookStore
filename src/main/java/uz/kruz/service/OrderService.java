package uz.kruz.service;

import uz.kruz.dto.BaseDTO;
import uz.kruz.domain.vo.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService<D extends BaseDTO> extends Service<D, Integer> {

    List<D> findByUserId(Integer userId);

    List<D> findByStatus(OrderStatus status);

    List<D> findByOrderDateAfter(LocalDateTime date);

    List<D> findByTotalAmountGreaterThan(Double amount);
}
