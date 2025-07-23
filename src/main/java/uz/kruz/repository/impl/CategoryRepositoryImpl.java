package uz.kruz.repository.impl;

import uz.kruz.db.DatabaseConnection;
import uz.kruz.domain.Category;
import uz.kruz.util.exceptions.DatabaseUnavailableException;
import uz.kruz.util.exceptions.RowNotFoundException;
import uz.kruz.util.exceptions.RepositoryException;
import uz.kruz.repository.CategoryRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryRepositoryImpl implements CategoryRepository {

    private final Connection connection;
    private static final String CREATE = "INSERT INTO categories (name) VALUES (?)";
    private static final String SELECT_BY_ID = "SELECT * FROM categories WHERE id=?";
    private static final String SELECT_ALL = "SELECT * FROM categories";
    private static final String DELETE = "DELETE FROM categories WHERE id=?";
    private static final String UPDATE = "UPDATE categories SET name=? WHERE id=?";
    private static final String COUNT = "SELECT COUNT(*) FROM categories";
    private static final String SELECT_BY_NAME = "SELECT * FROM categories WHERE name =?";

    public CategoryRepositoryImpl() {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new DatabaseUnavailableException("Failed to initialize database connection", e);
        }
    }

    @Override
    public Category create(Category entity) {
        try (PreparedStatement ps = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, entity.getName());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                entity.setId(rs.getInt(1));
            }
            return entity;
        } catch (SQLException e) {
            throw new RepositoryException("Error creating category: " + entity, e);
        }
    }

    @Override
    public Optional<Category> retrieveById(Integer id) {
        try (PreparedStatement ps = connection.prepareStatement(SELECT_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RepositoryException("Error retrieving category by id: " + id, e);
        }
    }

    @Override
    public List<Category> retrieveAll() {
        List<Category> list = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_ALL)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error retrieving all categories", e);
        }
        return list;
    }

    @Override
    public boolean deleteById(Integer id) {
        try (PreparedStatement ps = connection.prepareStatement(DELETE)) {
            ps.setInt(1, id);
            int deleted = ps.executeUpdate();
            if (deleted == 0) {
                throw new RowNotFoundException("Category with id " + id + " not found for deletion");
            }
            return true;
        } catch (SQLException e) {
            throw new RepositoryException("Error deleting category with id: " + id, e);
        }
    }

    @Override
    public Category update(Category entity) {
        try (PreparedStatement ps = connection.prepareStatement(UPDATE)) {
            ps.setString(1, entity.getName());
            ps.setInt(2, entity.getId());
            int updated = ps.executeUpdate();
            if (updated == 0) {
                throw new RowNotFoundException("Category with id " + entity.getId() + " not found for update");
            }
            return entity;
        } catch (SQLException e) {
            throw new RepositoryException("Error updating category: " + entity, e);
        }
    }

    @Override
    public long count() {
        try (PreparedStatement stmt = connection.prepareStatement(COUNT)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getLong(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new RepositoryException("Error counting categories", e);
        }
    }

    @Override
    public boolean existsById(Integer id) {
        try (PreparedStatement ps = connection.prepareStatement(SELECT_BY_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error checking existence for category id: " + id, e);
        }
    }

    @Override
    public Optional<Category> retrieveByName(String name) {
        try (PreparedStatement ps = connection.prepareStatement(SELECT_BY_NAME)) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RepositoryException("Error retrieving category by name: " + name, e);
        }
    }

    private Category mapRow(ResultSet rs) throws SQLException {
        return Category.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                .updatedAt(rs.getTimestamp("updated_at").toLocalDateTime())
                .build();
    }

    public static void main(String[] args) {
        CategoryRepositoryImpl categoryRepository = new CategoryRepositoryImpl();
        Category category = categoryRepository.retrieveById(3).orElseThrow();
        System.out.println(category.getName());
        System.out.println(categoryRepository.count());
    }
}
