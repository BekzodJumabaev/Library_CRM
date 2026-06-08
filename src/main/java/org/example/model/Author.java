package org.example.model;


import lombok.*;
import org.example.model.base.IdEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Author extends IdEntity {
    public String fullName;
}
