package org.example.service;

import org.example.mapper.AuthorMapper;
import org.example.model.Author;
import org.example.model.dto.AuthorCreateDTO;
import org.example.model.dto.AuthorDTO;
import org.example.model.dto.AuthorUpdateDTO;
import org.example.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    private final AuthorMapper mapper;
    private final AuthorRepository repository;

    public AuthorService(AuthorMapper mapper, @Qualifier("authorRepositoryDataBase") AuthorRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    public List<AuthorDTO> getAll() {
        List<Author> authors = repository.getAll();
        return mapper.toDTO(authors);
    }

    public AuthorDTO getById(Integer id) {
        Author author = repository.findById(id).orElseThrow(
                () -> new RuntimeException("Author Not Found!")
        );
        return mapper.toDTO(author);
    }

    public AuthorDTO create(AuthorCreateDTO dto) {
        Author author = mapper.fromDto(dto);
        Author save = repository.save(author);
        return mapper.toDTO(save);
    }
    public AuthorDTO update(Integer id, AuthorUpdateDTO dto) {
        Author author = repository.findById(id).orElseThrow(
                () -> new RuntimeException("Author Not Found!")
        );
        mapper.fromDto(author, dto);
        Author save = repository.save(author);
        return mapper.toDTO(save);
    }

    public void delete(Integer id) {
        Author author = repository.findById(id).orElseThrow(
                () -> new RuntimeException("Author Not Found!")
        );
        repository.delete(author);
    }
}
