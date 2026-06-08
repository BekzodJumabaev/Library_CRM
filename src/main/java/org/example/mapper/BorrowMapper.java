package org.example.mapper;

import org.example.model.Borrow;
import org.example.model.dto.BorrowCreateDTO;
import org.example.model.dto.BorrowDTO;
import org.example.model.dto.BorrowUpdateDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class BorrowMapper {

    public List<BorrowDTO> toDTO(List<Borrow> all) {
       return all.stream().map(this::toDTO).toList();
    }

    public BorrowDTO toDTO(Borrow borrow) {
        return BorrowDTO.builder()
                .id(borrow.getId())
                .userId(borrow.getUserId())
                .bookId(borrow.getBookId())
                .borrowDate(borrow.getBorrowDate())
                .deadline(borrow.getDeadline())
                .returnDate(borrow.getReturnDate())
                .build();
    }

    public Borrow fromDTO(BorrowCreateDTO borrowDTO) {
        Borrow borrow = Borrow.builder()
                .userId(borrowDTO.getUserId())
                .bookId(borrowDTO.getBookId())
                .borrowDate(LocalDateTime.now())
                .build();

        if (borrowDTO.getDeadline() != null && !borrowDTO.getDeadline().isEmpty()) {
            LocalDate deadline = LocalDate.parse(borrowDTO.getDeadline());
            borrow.setDeadline(deadline.atStartOfDay());
        }
        return borrow;
    }


    public Borrow fromDTO(Borrow borrow, BorrowUpdateDTO borrowDTO) {
        borrow.setUserId(borrowDTO.getUserId());
        borrow.setBookId(borrowDTO.getBookId());

        if (borrowDTO.getDeadline() != null && !borrowDTO.getDeadline().isEmpty()) {
            borrow.setDeadline(LocalDateTime.parse(borrowDTO.getDeadline()));
        }

        if (borrowDTO.getReturnDate() != null && !borrowDTO.getReturnDate().isEmpty()) {
            borrow.setReturnDate(LocalDateTime.parse(borrowDTO.getReturnDate()));
        } else {
            borrow.setReturnDate(null);
        }
        return borrow;
    }
}
