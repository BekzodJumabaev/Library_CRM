package org.example.model;

import lombok.*;
import org.example.model.base.BaseEntity;
import org.example.model.base.IdEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book extends BaseEntity {
    private String title;
    private Integer authorId;
    private String authorName;
    private String imageUrl;
    private Boolean available = true;
    private Integer count;
}
