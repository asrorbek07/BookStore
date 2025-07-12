package uz.kruz.repository;

import uz.kruz.domain.Author;

import java.util.List;

public interface AuthorRepository extends Repository<Author, Integer> {

    List<Author> retrieveByName(String name);

    List<Author> retrieveByBookId(Integer bookId);

}
