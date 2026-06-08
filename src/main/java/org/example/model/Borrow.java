package org.example.model;

import lombok.*;
import org.example.model.base.BaseEntity;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Borrow extends BaseEntity {
    private Integer userId;
    private Integer bookId;
    private LocalDateTime borrowDate;
    private LocalDateTime deadline;
    private LocalDateTime returnDate;
}
