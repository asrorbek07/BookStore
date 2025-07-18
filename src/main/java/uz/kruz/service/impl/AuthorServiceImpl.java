package uz.kruz.service.impl;

import uz.kruz.domain.Author;
import uz.kruz.domain.User;
import uz.kruz.dto.AuthorDTO;
import uz.kruz.repository.AuthorRepository;
import uz.kruz.service.AuthorService;
import uz.kruz.util.StringUtil;


import java.sql.*;
import java.util.*;

public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author register(AuthorDTO dto) {
        if (StringUtil.isEmpty(dto.getFullName())) {
            throw new IllegalArgumentException("full name must not be empty");
        }
        if(authorRepository.retrieveByName(dto.getFullName())!=null){
            throw new RuntimeException(String.format("Author with name %s already exists", dto.getFullName()));
        }

        Author author = Author.builder()
                .fullName(dto.getFullName())
                .build();


        return authorRepository.create(author);
    }

    @Override
    public Optional<Author> findById(Integer id) {


        return authorRepository.retrieveById(id);


    }

    @Override
    public List<Author> findAll() {
        return authorRepository.retrieveAll();

    }

    @Override
    public boolean removeById(Integer id) {

        return authorRepository.deleteById(id);
    }

    @Override
    public Author modify(AuthorDTO dto, Integer id) {
        Author author = authorRepository.retrieveById(id).orElseThrow(()-> new RuntimeException(String.format("Author with id %d does not exists", id)));
        if(dto.getFullName()!=null && ! StringUtil.isEmpty(dto.getFullName())){
            author.setFullName(dto.getFullName());
        }
        return authorRepository.update(author);
    }

    @Override
    public long count() {
        return authorRepository.count();
    }


    @Override
    public List<Author> findByName(String name) {
        if (name==null|| StringUtil.isEmpty(name)) {
            throw new IllegalArgumentException("name must not be empty or null");

        }
        return authorRepository.retrieveByName(name);
    }


    @Override
    public List<Author> findByBookId(Integer bookId) {
        if (bookId == null) {
            throw new IllegalArgumentException("bookId bo'sh bo'lmasligi kerak");
        }

        List<Author> authors = new ArrayList<>();
        String SELECT_FIND_BY_BOOK_ID = "SELECT * FROM author WHERE book_id = ?";

        try (
                Connection connection = DriverManager.getConnection(
                        "jdbc:mariadb://localhost:3306/your_database",
                        "your_username",
                        "your_password"
                );
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FIND_BY_BOOK_ID)
        ) {
            preparedStatement.setInt(1, bookId);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Author author = new Author();
                author.setId(rs.getLong("id"));
                author.setFullName(rs.getString("name"));
                authors.add(author);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return authors;
    }

}
