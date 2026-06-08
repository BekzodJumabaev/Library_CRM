package org.example.model.dto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDTO {
    private Integer id;
    private String title;
    private String authorName;
    private String imageUrl;
    private Boolean available;
    private Integer count;

}
