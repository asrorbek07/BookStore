package uz.kruz.service;

import uz.kruz.dto.BaseDTO;

import java.util.List;

public interface OrderItemService<D extends BaseDTO> extends Service<D, Integer> {

    List<D> findByOrderId(Integer orderId);

    List<D> findByBookId(Integer bookId);

    List<D> findByQuantityGreaterThan(Integer quantity);
}
