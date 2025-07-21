package uz.kruz.repository.impl;

import uz.kruz.db.DatabaseConnection;
import uz.kruz.domain.Publisher;
import uz.kruz.repository.OrderItemRepository;
import uz.kruz.repository.PublisherRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PublisherRepositoryImpl implements PublisherRepository {
    private final String INSERT = "INSERT INTO publishers(name, contact_email, phone) VALUES (?, ?, ?)";
    private final String SELECT = "SELECT * FROM publishers WHERE id = ?";
    private final String SELECT_ALL = "SELECT * FROM publishers";
    private final String DELETE = "DELETE FROM publishers WHERE id = ?";
    private final String UPDATE = "UPDATE publishers SET name = ?, contact_email = ?, phone = ? WHERE id = ?";
    private final String COUNT = "SELECT COUNT(*) FROM publishers";
    private final String SELECT_BY_NAME = "SELECT * FROM publishers WHERE name LIKE ?";
    private final String SELECT_BY_EMAIL = "SELECT * FROM publishers WHERE contact_email = ?";

    private final Connection connection;

    public PublisherRepositoryImpl() {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database connection", e);
        }
    }

    @Override
    public Publisher create(Publisher entity) {

        try(PreparedStatement ps = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, entity.getName());
            ps.setString(2, entity.getContactEmail());
            ps.setString(3, entity.getPhone());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                entity.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new UnsupportedOperationException("Method not implemented", e);
        }
        return entity;
    }

    @Override
    public Optional<Publisher> retrieveById(Integer id) {

        try(PreparedStatement ps = connection.prepareStatement(SELECT)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return  Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new UnsupportedOperationException("Method not implemented", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Publisher> retrieveAll() {
        List<Publisher> publishers = new ArrayList<>();

        try(Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(SELECT_ALL);
            while (rs.next()) {
                publishers.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new UnsupportedOperationException("Method not implemented", e);
        }
        return publishers;
    }

    @Override
    public boolean deleteById(Integer id) {

        try(PreparedStatement ps = connection.prepareStatement(DELETE)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            throw new UnsupportedOperationException("Method not implemented", e);
        }
    }

    @Override
    public Publisher update(Publisher entity) {

        try(PreparedStatement ps = connection.prepareStatement(UPDATE)) {
            ps.setString(1, entity.getName());
            ps.setString(2, entity.getContactEmail());
            ps.setString(3, entity.getPhone());
            ps.setInt(4, entity.getId());
            ps.executeUpdate();
            return  entity;
        } catch (Exception e) {
            throw new UnsupportedOperationException("Method not implemented", e);
        }
    }

    @Override
    public long count() {

        try(Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(COUNT);
            if (rs.next()) {
                return rs.getLong(1);
            }
        }catch (Exception e) {
            throw new UnsupportedOperationException("Method not implemented");
        }
        return 0;
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public Optional<Publisher> retrieveByName(String name) {
        try(PreparedStatement ps = connection.prepareStatement(SELECT_BY_NAME)) {
            ps.setString(1, "%" + name + "%");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Optional.of(mapRow(rs));
            }
        } catch (Exception e) {
            throw new UnsupportedOperationException("Method not implemented", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Publisher> retrieveByEmail(String email) {
        List<Publisher> publishers = new ArrayList<>();

        try(PreparedStatement ps = connection.prepareStatement(SELECT_BY_EMAIL)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                publishers.add(mapRow(rs));
            }
        } catch (Exception e) {
            throw new UnsupportedOperationException("Method not implemented", e);
        }
        return publishers;
    }

    private Publisher mapRow(ResultSet rs) throws SQLException {
        return Publisher.builder()
        .id(rs.getInt("id"))
        .name(rs.getString("name"))
        .contactEmail(rs.getString("contact_email"))
        .phone(rs.getString("phone"))
        .build();
    }

//    public static void main(String[] args) {
//        PublisherRepository publisherRepository = new PublisherRepositoryImpl();
//        publisherRepository.retrieveAll().forEach(
//                publisher -> {
//                    System.out.println("Publisher ID: " + publisher.getId());
//                    System.out.println("Publisher Name: " + publisher.getName());
//                    System.out.println("Publisher Email: " + publisher.getContactEmail());
//                    System.out.println("Publisher Phone: " + publisher.getPhone());
//                    System.out.println("-----------------------------");
//
//                }
//        );
//    }
}
