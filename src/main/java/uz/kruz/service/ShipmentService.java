package uz.kruz.service;

import uz.kruz.dto.BaseDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ShipmentService<D extends BaseDTO> extends Service<D, Integer> {

    Optional<D> findByTrackingNumber(String trackingNumber);

    List<D> findByOrderId(Integer orderId);

    List<D> findByShippedAtAfter(LocalDateTime date);

    List<D> findByDeliveryEstimateBefore(LocalDate date);
}