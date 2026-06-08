package org.example.service;

import org.example.mapper.BorrowMapper;
import org.example.model.Book;
import org.example.model.Borrow;
import org.example.model.dto.BorrowCreateDTO;
import org.example.model.dto.BorrowDTO;
import org.example.model.dto.BorrowUpdateDTO;
import org.example.repository.BookRepository;
import org.example.repository.BorrowRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class BorrowService {

    private final BorrowRepository borrowRepository;
    private final BorrowMapper mapper;
    private final BookRepository bookRepository;

    public BorrowService(@Qualifier("borrowRepositoryDataBase") BorrowRepository borrowRepository, BorrowMapper mapper, @Qualifier("bookRepositoryDataBase") BookRepository bookRepository) {
        this.borrowRepository = borrowRepository;
        this.mapper = mapper;
        this.bookRepository = bookRepository;
    }

    public List<BorrowDTO> getAll() {
        List<Borrow> all = borrowRepository.getAll();
        return mapper.toDTO(all);
    }

    public BorrowDTO create(BorrowCreateDTO borrowDTO) {

        Book book = bookRepository.getById(borrowDTO.getBookId())
                .orElseThrow(() -> new RuntimeException("Kitob topilmadi"));

        if (book.getCount() <= 0 ){
            throw new RuntimeException("Kechirasiz, bu kitob hozircha tugagan.");
        }

        book.setCount(book.getCount() - 1);
        bookRepository.save(book);
        Borrow borrow = mapper.fromDTO(borrowDTO);
        borrow.setCreatedAt(LocalDateTime.now());
        borrowRepository.save(borrow);
        return mapper.toDTO(borrow);
    }

    public BorrowDTO getById(Integer id) {
        Borrow borrow = borrowRepository.getById(id).orElseThrow(
                () -> new RuntimeException("Borrow Not Found")
        );
        return mapper.toDTO(borrow);
    }

    public List<BorrowDTO> getByUserId(Integer id) {
        List<Borrow> allByUserId = borrowRepository.findAllByUserId(id);
        return mapper.toDTO(allByUserId);
    }

    public BorrowDTO update(Integer id, BorrowUpdateDTO borrowDTO) {
        Borrow borrow = borrowRepository.getById(id).orElseThrow(
                () -> new RuntimeException("Ijara topilmadi")
        );
        if (borrow.getReturnDate() != null) {
            throw new IllegalStateException("Bu ijara allaqachon yopilgan");
        }

        mapper.fromDTO(borrow, borrowDTO);
        borrow.setUpdatedAt(LocalDateTime.now());

        if (borrow.getReturnDate() != null) {
            bookRepository.updateBookCount(borrow.getId(), 1);
        }

        borrowRepository.save(borrow);
        return mapper.toDTO(borrow);
    }
}
