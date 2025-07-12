package uz.kruz.repository.impl;

import uz.kruz.db.DatabaseConnection;
import uz.kruz.domain.Shipment;
import uz.kruz.repository.ShipmentRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class ShipmentRepositoryImpl implements ShipmentRepository {

    private final Connection connection;

    public ShipmentRepositoryImpl() {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database connection", e);
        }
    }

    @Override
    public Shipment create(Shipment entity) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public Optional<Shipment> retrieveById(Integer id) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<Shipment> retrieveAll() {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public boolean deleteById(Integer id) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public Shipment update(Shipment entity) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public Optional<Shipment> retrieveByTrackingNumber(String trackingNumber) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<Shipment> retrieveByOrderId(Integer orderId) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<Shipment> retrieveByShippedAtAfter(LocalDateTime date) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<Shipment> retrieveByDeliveryEstimateBefore(LocalDate date) {
        throw new UnsupportedOperationException("Method not implemented");
    }
}
