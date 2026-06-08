package org.example.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookUpdateDTO {
    private String title;
    private Integer authorId;
    private String imageUrl;
    private Integer count;
}
