package org.example.service;

import org.example.mapper.BookMapper;
import org.example.model.Book;
import org.example.model.dto.BookCreateDTO;
import org.example.model.dto.BookDTO;
import org.example.model.dto.BookUpdateDTO;
import org.example.repository.BookRepository;
import org.example.repository.InMemory.BookRepositoryDB;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class BookService {

    private final BookMapper mapper;
    private final BookRepository bookRepository;

    public BookService(BookMapper mapper, @Qualifier("bookRepositoryDataBase") BookRepository bookRepository) {
        this.mapper = mapper;
        this.bookRepository = bookRepository;
    }


    public List<BookDTO> getAllBooks() {
        List<Book> all = bookRepository.getAll();
        return mapper.toDTO(all);
    }

    public BookDTO create(BookCreateDTO dto) {
        Book book = mapper.fromDTO(dto);
        book.setCreatedAt(LocalDateTime.now());
        Book save = bookRepository.save(book);
        return mapper.toDTO(save);
    }


    public BookDTO getById(Integer id) {
        Book book = bookRepository.getById(id).orElseThrow(
                () -> new RuntimeException("Book Not Found!")
        );
        return mapper.toDTO(book);
    }

    public BookDTO update(Integer id, BookUpdateDTO dto) {
        Book book = bookRepository.getById(id).orElseThrow(
                () -> new RuntimeException("Book Not Found!")
        );
        mapper.fromDTO(book, dto);
        book.setUpdatedAt(LocalDateTime.now());
        Book save = bookRepository.save(book);
        return mapper.toDTO(save);
    }

    public void delete(Integer id) {
        Book book = bookRepository.getById(id).orElseThrow(
                () -> new RuntimeException("Book Not Found!")
        );
        bookRepository.delete(book);
    }
}
