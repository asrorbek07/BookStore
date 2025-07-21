package uz.kruz.service.impl;

import uz.kruz.domain.Shipment;
import uz.kruz.dto.ShipmentDTO;
import uz.kruz.repository.ShipmentRepository;
import uz.kruz.service.ShipmentService;
import uz.kruz.util.Check.ShipmentCheck;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class ShipmentServiceImpl implements ShipmentService {

    private final ShipmentRepository shipmentRepository;

    public ShipmentServiceImpl(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    @Override
    public Shipment register(ShipmentDTO dto) {
        ShipmentCheck.registerCheck(dto);
        Shipment shipment = Shipment.builder()
                .orderId(dto.getOrderId())
                .trackingNumber(dto.getTrackingNo())
                .shippedAt(dto.getShippedAt().toLocalDateTime())
                .deliveryEstimate(dto.getDeliveryEstimate().toLocalDate()).build();
        return shipmentRepository.create(shipment);
    }

    @Override
    public Optional<Shipment> findById(Integer id) {
        ShipmentCheck.findByIdCheck(id);
        return  shipmentRepository.retrieveById(id);
    }

    @Override
    public List<Shipment> findAll() {
        return shipmentRepository.retrieveAll();
    }

    @Override
    public boolean removeById(Integer id) {
        ShipmentCheck.removeByIdCheck(id);
        return shipmentRepository.deleteById(id);
    }

    @Override
    public Shipment modify(ShipmentDTO dto, Integer id) {
        ShipmentCheck.modifyCheck(dto, id);
        Shipment existingShipment = shipmentRepository.retrieveById(id)
                .orElseThrow(() -> new IllegalArgumentException("Shipment with id " + id + " not found"));

        existingShipment.setOrderId(dto.getOrderId());
        existingShipment.setTrackingNumber(dto.getTrackingNo());
        existingShipment.setShippedAt(dto.getShippedAt().toLocalDateTime());
        existingShipment.setDeliveryEstimate(dto.getDeliveryEstimate().toLocalDate());
        return shipmentRepository.update(existingShipment);
    }

    @Override
    public long count() {
        return shipmentRepository.count();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public Optional<Shipment> findByTrackingNumber(String trackingNumber) {
        ShipmentCheck.findByTrackingNumberCheck(trackingNumber);
        return shipmentRepository.retrieveByTrackingNumber(trackingNumber);
    }

    @Override
    public List<Shipment> findByOrderId(Integer orderId) {
        ShipmentCheck.findByOrderIdCheck(orderId);
        return shipmentRepository.retrieveByOrderId(orderId);
    }

    @Override
    public List<Shipment> findByShippedAtAfter(LocalDateTime date) {
        ShipmentCheck.findByShippedAtCheck(date);
        return shipmentRepository.retrieveByShippedAtAfter(date);
    }

    @Override
    public List<Shipment> findByDeliveryEstimateBefore(LocalDate date) {
        ShipmentCheck.findByDeliveryEstimateCheck(date.atStartOfDay());
        return shipmentRepository.retrieveByDeliveryEstimateBefore(date);
    }
}
