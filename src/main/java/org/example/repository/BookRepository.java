package org.example.repository;

import org.example.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    List<Book> getAll();
    Optional<Book> getById(Integer id);
    Book save(Book book);
    void delete(Book book);

    void updateBookCount(Integer id, int i);
}
