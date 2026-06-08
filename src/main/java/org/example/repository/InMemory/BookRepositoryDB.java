package org.example.repository.InMemory;
import org.example.model.Book;
import org.example.repository.BookRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class BookRepositoryDB implements BookRepository {
    List<Book> BOOKS = new CopyOnWriteArrayList<>();

    @Override
    public List<Book> getAll() {
        return BOOKS.stream().filter(
                book -> book.getDeleted() == null || !book.getDeleted()).toList();
    }

    @Override
    public Optional<Book> getById(Integer id) {
        return BOOKS.stream().filter(book -> book.getId().equals(id)).findFirst();
    }

    @Override
    public Book save(Book book) {
        Optional<Book> byId = getById(book.getId());
        byId.ifPresent(BOOKS::remove);
        BOOKS.add(book);
        return book;
    }

    @Override
    public void delete(Book book) {
        book.setDeleted(true);
        BOOKS.add(book);
    }

    @Override
    public void updateBookCount(Integer id, int i) {

    }
}
