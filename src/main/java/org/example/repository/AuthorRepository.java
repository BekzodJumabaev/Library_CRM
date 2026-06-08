package org.example.repository;

import org.example.model.Author;
import org.example.model.dto.AuthorDTO;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {

    List<Author> getAll();

    Optional<Author> findById (Integer id);

    Author save(Author author);

    void delete (Author author);
}
