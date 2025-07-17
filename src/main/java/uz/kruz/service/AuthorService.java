package uz.kruz.service;

import uz.kruz.domain.Author;
import uz.kruz.dto.AuthorDTO;
import uz.kruz.dto.BaseDTO;

import java.util.List;

public interface AuthorService extends Service<AuthorDTO, Author, Integer> {

    List<Author> findByName(String name);

    List<Author> findByBookId(Integer bookId);
}
