package org.example.model.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BorrowDTO {
    private Integer id;
    private Integer userId;
    private Integer bookId;
    private LocalDateTime borrowDate;
    private  LocalDateTime deadline;
    private LocalDateTime returnDate;
}
