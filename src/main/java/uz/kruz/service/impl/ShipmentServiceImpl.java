package uz.kruz.service.impl;

import uz.kruz.domain.Shipment;
import uz.kruz.dto.ShipmentDTO;
import uz.kruz.repository.ShipmentRepository;
import uz.kruz.service.ShipmentService;

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
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public Optional<Shipment> findById(Integer id) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<Shipment> findAll() {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public boolean removeById(Integer id) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public Shipment modify(ShipmentDTO dto, Integer id) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public Optional<Shipment> findByTrackingNumber(String trackingNumber) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<Shipment> findByOrderId(Integer orderId) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<Shipment> findByShippedAtAfter(LocalDateTime date) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<Shipment> findByDeliveryEstimateBefore(LocalDate date) {
        throw new UnsupportedOperationException("Method not implemented");
    }
}
