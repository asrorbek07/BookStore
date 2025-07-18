package uz.kruz.repository.impl;

import uz.kruz.db.DatabaseConnection;
import uz.kruz.domain.Shipment;
import uz.kruz.repository.ShipmentRepository;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ShipmentRepositoryImpl implements ShipmentRepository {
    private final String INSERT = "INSERT INTO shipments (order_id, tracking_number, shipped_at, delivery_estimate) VALUES (?, ?, ?, ?)";
    private final String SELECT = "SELECT * FROM shipments WHERE id = ?";
    private final String SELECT_ALL = "SELECT * FROM shipments";
    private final String DELETE = "DELETE FROM shipments WHERE id = ?";
    private final String UPDATE = "UPDATE shipments SET order_id = ?, tracking_number = ?,  shipped_at = ?, delivery_estimate = ? WHERE id = ?";
    private final String COUNT = "SELECT COUNT(*) FROM shipments";
    private final String SELECT_BY_TM = "SELECT * FROM shipments WHERE tracking_number = ?";
    private final String SELECT_BY_ORDER_ID = "SELECT * FROM shipments WHERE order_id = ?";
    private final String SELECT_BY_SHIPPED_AT = "SELECT * FROM shipments WHERE shipped_at <= ?";
    private final String SELECT_BY_DELIVERY = "SELECT * FROM shipments WHERE delivery_estimate > ?";
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

        try (PreparedStatement ps = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, entity.getOrderId());
            ps.setString(2, entity.getTrackingNumber());
            ps.setTimestamp(3, Timestamp.valueOf(entity.getShippedAt()));
            ps.setDate(4, Date.valueOf(entity.getDeliveryEstimate()));
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                entity.setId(rs.getInt(1));
            }
            return entity;
        } catch (Exception e) {
            throw new UnsupportedOperationException("Method not implemented", e);
        }
    }

    @Override
    public Optional<Shipment> retrieveById(Integer id) {

        try (PreparedStatement ps = connection.prepareStatement(SELECT)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new UnsupportedOperationException("Method not implemented", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Shipment> retrieveAll() {
        List<Shipment> shipments = new ArrayList<>();

        try(Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(SELECT_ALL);
            while (rs.next()) {
                shipments.add(mapRow(rs));
            }
        } catch (Exception e) {
            throw new UnsupportedOperationException("Method not implemented", e);
        }
        return shipments;
    }

    @Override
    public boolean deleteById(Integer id) {

        try(PreparedStatement ps = connection.prepareStatement(DELETE)) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        } catch (Exception e) {
            throw new UnsupportedOperationException("Method not implemented", e);
        }
    }

    @Override
    public Shipment update(Shipment entity) {

        try(PreparedStatement ps = connection.prepareStatement(UPDATE)) {
            ps.setInt(1, entity.getOrderId());
            ps.setString(2, entity.getTrackingNumber());
            ps.setTimestamp(3, Timestamp.valueOf(entity.getShippedAt()));
            ps.setDate(4, Date.valueOf(entity.getDeliveryEstimate()));
            ps.setInt(5, entity.getId());
            ps.executeUpdate();
            return entity;
        } catch (Exception e) {

            throw new UnsupportedOperationException("Method not implemented");
        }
    }

    @Override
    public long count() {

        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(COUNT);
            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new UnsupportedOperationException("Method not implemented", e);
        }
        return 0;
    }

    @Override
    public Optional<Shipment> retrieveByTrackingNumber(String trackingNumber) {

        try(PreparedStatement ps = connection.prepareStatement(SELECT_BY_TM)) {
            ps.setString(1, trackingNumber);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }
        } catch (Exception e) {

            throw new UnsupportedOperationException("Method not implemented", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Shipment> retrieveByOrderId(Integer orderId) {
        List<Shipment> shipments = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(SELECT_BY_ORDER_ID)) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                shipments.add(mapRow(rs));
            }
        } catch (Exception e) {

            throw new UnsupportedOperationException("Method not implemented", e);
        }
        return shipments;
    }

    @Override
    public List<Shipment> retrieveByShippedAtAfter(LocalDateTime date) {
        List<Shipment> shipments = new ArrayList<>();

        try(PreparedStatement ps = connection.prepareStatement(SELECT_BY_SHIPPED_AT)) {
            ps.setTimestamp(1, Timestamp.valueOf(date));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                shipments.add(mapRow(rs));
            }
        } catch (Exception e) {

            throw new UnsupportedOperationException("Method not implemented", e);
        }
        return shipments;
    }

    @Override
    public List<Shipment> retrieveByDeliveryEstimateBefore(LocalDate date) {
        List<Shipment> shipments = new ArrayList<>();

        try(PreparedStatement ps = connection.prepareStatement(SELECT_BY_DELIVERY)) {
            ps.setDate(1, Date.valueOf(date));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                shipments.add(mapRow(rs));
            }
        } catch (Exception e) {

            throw new UnsupportedOperationException("Method not implemented", e);
        }
        return shipments;
    }

    private Shipment mapRow(ResultSet rs) throws SQLException {
        return Shipment.builder()
                .id(rs.getInt("id"))
                .orderId(rs.getInt("order_id"))
                .trackingNumber(rs.getString("tracking_number"))
                .shippedAt(rs.getTimestamp("shipped_at").toLocalDateTime())
                .deliveryEstimate(rs.getDate("delivery_estimate").toLocalDate())
                .build();
    }
    public static void main(String[] args) {
        ShipmentRepository shipmentRepository = new ShipmentRepositoryImpl();
        shipmentRepository.retrieveAll().forEach(
                shipment -> {
                    System.out.println("Shipment ID: " + shipment.getId());
                    System.out.println("Shipment OrderID: " + shipment.getOrderId());
                    System.out.println("Shipment TrackNum: " + shipment.getTrackingNumber());
                    System.out.println("Shipment ShippedAt: " + shipment.getShippedAt());
                    System.out.println("Shipment DeliveryEstimate: " + shipment.getDeliveryEstimate());
                    System.out.println("-----------------------------");

                }
        );
    }
}