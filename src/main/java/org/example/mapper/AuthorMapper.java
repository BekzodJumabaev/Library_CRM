package org.example.mapper;

import org.example.model.Author;
import org.example.model.dto.AuthorCreateDTO;
import org.example.model.dto.AuthorDTO;
import org.example.model.dto.AuthorUpdateDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthorMapper {
    public List<AuthorDTO> toDTO(List<Author> authors) {
        return authors.stream().map(this::toDTO).toList();
    }

    public AuthorDTO toDTO(Author authors) {
        return AuthorDTO.builder()
                .id(authors.getId())
                .fullName(authors.getFullName())
                .build();
    }

    public Author fromDto(AuthorCreateDTO dto) {
            Author author = new Author();
            author.setFullName(dto.getFullName());
            return author;
    }

    public Author fromDto(Author author, AuthorUpdateDTO dto) {
        author.setFullName(dto.getFullName());
        return author;
    }
}
