package uz.kruz.service.impl;

import uz.kruz.dto.BaseDTO;
import uz.kruz.repository.ShipmentRepository;
import uz.kruz.service.ShipmentService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class ShipmentServiceImpl<D extends BaseDTO> implements ShipmentService<D> {

    private final ShipmentRepository shipmentRepository;

    public ShipmentServiceImpl(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    @Override
    public D register(D dto) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public Optional<D> findById(Integer id) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<D> findAll() {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public boolean removeById(Integer id) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public D modify(D dto) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public Optional<D> findByTrackingNumber(String trackingNumber) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<D> findByOrderId(Integer orderId) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<D> findByShippedAtAfter(LocalDateTime date) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<D> findByDeliveryEstimateBefore(LocalDate date) {
        throw new UnsupportedOperationException("Method not implemented");
    }
}
