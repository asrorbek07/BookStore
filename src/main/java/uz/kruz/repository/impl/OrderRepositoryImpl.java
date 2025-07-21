package uz.kruz.repository.impl;

import uz.kruz.db.DatabaseConnection;
import uz.kruz.domain.Order;
import uz.kruz.domain.vo.OrderStatus;
import uz.kruz.repository.OrderRepository;
import uz.kruz.util.exceptions.DatabaseUnavailableException;
import uz.kruz.util.exceptions.RowNotFoundException;
import uz.kruz.util.exceptions.RepositoryException;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderRepositoryImpl implements OrderRepository {

    private final Connection connection;

    private static final String INSERT = "INSERT INTO orders(user_id, total_amount, status) VALUES (?, ?, ?)";
    private static final String SELECT_BY_ID = "SELECT * FROM orders WHERE id = ?";
    private static final String SELECT_ALL = "SELECT * FROM orders";
    private static final String DELETE_BY_ID = "DELETE FROM orders WHERE id = ?";
    private static final String UPDATE = "UPDATE orders SET user_id = ?, total_amount = ?, status = ? WHERE id = ?";
    private static final String COUNT = "SELECT COUNT(*) FROM orders";
    private static final String SELECT_BY_USER_ID = "SELECT * FROM orders WHERE user_id = ?";
    private static final String SELECT_BY_STATUS = "SELECT * FROM orders WHERE status = ?";
    private static final String SELECT_BY_DATE = "SELECT * FROM orders WHERE order_date < ?";
    private static final String SELECT_BY_AMOUNT = "SELECT * FROM orders WHERE total_amount > ?";

    public OrderRepositoryImpl() {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new DatabaseUnavailableException("Failed to initialize DB connection", e);
        }
    }

    @Override
    public Order create(Order entity) {
        try (PreparedStatement ps = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, entity.getUserId());
            ps.setBigDecimal(2, entity.getTotalAmount());
            ps.setString(3, entity.getStatus().name());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                entity.setId(rs.getInt(1));
            }
            return entity;
        } catch (SQLException e) {
            throw new RepositoryException("Failed to insert Order: " + entity, e);
        }
    }

    @Override
    public Optional<Order> retrieveById(Integer id) {
        try (PreparedStatement ps = connection.prepareStatement(SELECT_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error retrieving order by id: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Order> retrieveAll() {
        List<Order> orders = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(SELECT_ALL);
            while (rs.next()) {
                orders.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error retrieving all orders", e);
        }
        return orders;
    }

    @Override
    public boolean deleteById(Integer id) {
        try (PreparedStatement ps = connection.prepareStatement(DELETE_BY_ID)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            if (rows == 0) {
                throw new RowNotFoundException("No order found with id: " + id);
            }
            return true;
        } catch (SQLException e) {
            throw new RepositoryException("Error deleting order with id: " + id, e);
        }
    }

    @Override
    public Order update(Order entity) {
        try (PreparedStatement ps = connection.prepareStatement(UPDATE)) {
            ps.setInt(1, entity.getUserId());
            ps.setBigDecimal(2, entity.getTotalAmount());
            ps.setString(3, entity.getStatus().name());
            int updated = ps.executeUpdate();
            if (updated == 0) {
                throw new RowNotFoundException("Order not found for update: " + entity.getId());
            }
            return entity;
        } catch (SQLException e) {
            throw new RepositoryException("Error updating order: " + entity, e);
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
            throw new RepositoryException("Error counting orders", e);
        }
        return 0;
    }

    @Override
    public boolean existsById(Integer id) {
        try (PreparedStatement ps = connection.prepareStatement(SELECT_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RepositoryException("Error checking existence of order by id: " + id, e);
        }
    }

    @Override
    public List<Order> retrieveByUserId(Integer userId) {
        List<Order> orders = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SELECT_BY_USER_ID)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                orders.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error retrieving orders by userId: " + userId, e);
        }
        return orders;
    }

    @Override
    public List<Order> retrieveByStatus(OrderStatus status) {
        List<Order> orders = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SELECT_BY_STATUS)) {
            ps.setString(1, status.name());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                orders.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error retrieving orders by status: " + status, e);
        }
        return orders;
    }

    @Override
    public List<Order> retrieveByOrderDateAfter(LocalDateTime date) {
        List<Order> orders = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SELECT_BY_DATE)) {
            ps.setTimestamp(1, Timestamp.valueOf(date));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                orders.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error retrieving orders after date: " + date, e);
        }
        return orders;
    }

    @Override
    public List<Order> retrieveByTotalAmountGreaterThan(Double amount) {
        List<Order> orders = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SELECT_BY_AMOUNT)) {
            ps.setBigDecimal(1, BigDecimal.valueOf(amount));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                orders.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error retrieving orders with totalAmount > " + amount, e);
        }
        return orders;
    }

    private Order mapRow(ResultSet rs) throws SQLException {
        return Order.builder()
                .id(rs.getInt("id"))
                .userId(rs.getInt("user_id"))
                .totalAmount(rs.getBigDecimal("total_amount"))
                .status(OrderStatus.valueOf(rs.getString("status")))
                .orderDate(rs.getTimestamp("order_date").toLocalDateTime())
                .updatedAt(rs.getTimestamp("updated_at").toLocalDateTime())
                .build();
    }
}
