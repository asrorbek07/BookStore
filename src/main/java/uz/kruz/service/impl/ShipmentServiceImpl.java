package uz.kruz.service.impl;

import uz.kruz.domain.Shipment;
import uz.kruz.dto.ShipmentDTO;
import uz.kruz.repository.OrderRepository;
import uz.kruz.repository.ShipmentRepository;
import uz.kruz.service.ShipmentService;
import uz.kruz.util.Validator;
import uz.kruz.util.exceptions.EntityNotFoundException;
import uz.kruz.util.exceptions.ServiceException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.List;
import java.util.Optional;

public class ShipmentServiceImpl implements ShipmentService {

    private final ShipmentRepository shipmentRepository;
    private final OrderRepository orderRepository;

    public ShipmentServiceImpl(ShipmentRepository shipmentRepository, OrderRepository orderRepository) {
        this.shipmentRepository = shipmentRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public Shipment register(ShipmentDTO dto) {
        if (dto == null) {
            throw new ServiceException("ShipmentDTO is required");
        }
        Validator.validateInteger(dto.getOrderId(), "orderId");
        Validator.validateString(dto.getTrackingNo(), "trackingNo");
//        if (dto.getDeliveryEstimate() != null) {
//            Validator.validateString(String.valueOf(dto.getDeliveryEstimate()), "Delivery Estimate");
//        }
//        LocalDateTime now = LocalDateTime.now();
//        LocalDateTime shippedAt = dto.getShippedAt().toLocalDateTime();
//        if (shippedAt.isAfter(now)) {
//            throw new ServiceException("ShippedAt cannot be in the future");
//        }
//        LocalDate deliveryEstimate = dto.getDeliveryEstimate().toLocalDate();
//        if (deliveryEstimate.isBefore(shippedAt.toLocalDate())) {
//            throw new ServiceException("DeliveryEstimate cannot be before ShippedAt");
//        }
        Shipment shipment = Shipment.builder()
                .orderId(dto.getOrderId())
                .trackingNumber(dto.getTrackingNo())
                .build();
        return shipmentRepository.create(shipment);
    }

    @Override
    public Optional<Shipment> findById(Integer id) {
        Validator.validateInteger(id, "id");
        return shipmentRepository.retrieveById(id);
    }

    @Override
    public List<Shipment> findAll() {
        return shipmentRepository.retrieveAll();
    }

    @Override
    public boolean removeById(Integer id) {
        Validator.validateInteger(id, "id");
        return shipmentRepository.deleteById(id);
    }

    @Override
    public Shipment modify(ShipmentDTO dto, Integer id) {
        Validator.validateInteger(id, "id");
        Shipment existingShipment = shipmentRepository.retrieveById(id)
                .orElseThrow(() -> new EntityNotFoundException("Shipment with id " + id + " not found"));

        boolean modified = false;
        if (dto.getOrderId() != null) {
            Validator.validateInteger(dto.getOrderId(), "orderId");
            if (!orderRepository.existsById(dto.getOrderId())) {
                throw new EntityNotFoundException("Order with id " + dto.getOrderId() + " not found");
            }
            existingShipment.setOrderId(dto.getOrderId());
            modified = true;
        }
        if (dto.getTrackingNo() != null) {
            Validator.validateString(dto.getTrackingNo(), "trackingNo");
            existingShipment.setTrackingNumber(dto.getTrackingNo());
            modified = true;
        }
        if (dto.getShippedAt() != null) {
            existingShipment.setShippedAt(dto.getShippedAt().toLocalDateTime());
            modified = true;
        }
        if (dto.getDeliveryEstimate() != null) {
            existingShipment.setDeliveryEstimate(dto.getDeliveryEstimate().toLocalDate());
            modified = true;
        }
        if (!modified) {
            throw new ServiceException("No fields provided for update");
        }
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime shippedAt = dto.getShippedAt().toLocalDateTime();

        if (shippedAt.isAfter(now)) {
            throw new ServiceException("ShippedAt cannot be in the future");
        }
        LocalDate deliveryEstimate = dto.getDeliveryEstimate().toLocalDate();
        if (deliveryEstimate.isBefore(shippedAt.toLocalDate())) {
            throw new ServiceException("DeliveryEstimate cannot be before ShippedAt");
        }
        return shipmentRepository.update(existingShipment);
    }

    @Override
    public long count() {
        return shipmentRepository.count();
    }

    @Override
    public boolean existsById(Integer integer) {
        return shipmentRepository.existsById(integer);
    }

    @Override
    public Optional<Shipment> findByTrackingNumber(String trackingNumber) {
        Validator.validateString(trackingNumber, "trackingNumber");
        return shipmentRepository.retrieveByTrackingNumber(trackingNumber);
    }

    @Override
    public List<Shipment> findByOrderId(Integer orderId) {
        Validator.validateInteger(orderId, "orderId");
        return shipmentRepository.retrieveByOrderId(orderId);
    }

    @Override
    public List<Shipment> findByShippedAtAfter(LocalDateTime date) {
        LocalDateTime now = LocalDateTime.now();
        if (date.isAfter(now)) {
            throw new ServiceException("ShippedAt cannot be in the future");
        }
        return shipmentRepository.retrieveByShippedAtAfter(date);
    }

    @Override
    public List<Shipment> findByDeliveryEstimateBefore(LocalDate date) {
        LocalDateTime now = LocalDateTime.now();
        if (date.isBefore(ChronoLocalDate.from(now))) {
            throw new ServiceException("DeliveryEstimate cannot be before now");
        }
        return shipmentRepository.retrieveByDeliveryEstimateBefore(date);
    }
}
