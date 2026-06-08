package org.example.mapper;

import org.example.model.Book;
import org.example.model.dto.BookCreateDTO;
import org.example.model.dto.BookDTO;
import org.example.model.dto.BookUpdateDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookMapper {

    public BookDTO toDTO(Book book) {
        return BookDTO.builder()
                .id(book.getId())
                .count(book.getCount())
                .title(book.getTitle())
                .imageUrl(book.getImageUrl())
                .authorName(book.getAuthorName())
                .available(book.getAvailable())
                .build();
    }

    public List<BookDTO> toDTO(List<Book> books) {
        return books.stream().map(this::toDTO).toList();
    }

    public Book fromDTO(BookCreateDTO dto){
        return Book.builder()
                .count(dto.getCount())
                .title(dto.getTitle())
                .imageUrl(dto.getImageUrl())
                .authorId(dto.getAuthorId())
                .available(true)
                .build();
    }

    public Book fromDTO(Book book, BookUpdateDTO dtoUpdate){
        book.setTitle(dtoUpdate.getTitle());
        book.setAuthorId(dtoUpdate.getAuthorId());
        return book;
    }
}
