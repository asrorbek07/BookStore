package uz.kruz.service;

import uz.kruz.domain.Shipment;
import uz.kruz.dto.ShipmentDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ShipmentService extends Service<ShipmentDTO, Shipment, Integer> {

    Optional<Shipment> findByTrackingNumber(String trackingNumber);

    List<Shipment> findByOrderId(Integer orderId);

    List<Shipment> findByShippedAtAfter(LocalDateTime date);

    List<Shipment> findByDeliveryEstimateBefore(LocalDate date);
}