package org.example.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookCreateDTO {
    private String title;
    private Integer authorId;
    private String imageUrl;
    private Integer count;
}
