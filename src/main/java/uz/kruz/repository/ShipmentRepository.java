package uz.kruz.repository;

import uz.kruz.domain.Shipment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ShipmentRepository extends Repository<Shipment, Integer> {

    Optional<Shipment> retrieveByTrackingNumber(String trackingNumber);

    List<Shipment> retrieveByOrderId(Integer orderId);

    List<Shipment> retrieveByShippedAtAfter(LocalDateTime date);

    List<Shipment> retrieveByDeliveryEstimateBefore(LocalDate date);
}
