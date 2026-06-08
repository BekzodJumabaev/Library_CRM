package org.example.repository.InMemory;

import org.example.model.Author;
import org.example.repository.AuthorRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class AuthorRepositoryInMem implements AuthorRepository {
    
    private static final List<Author> AUTHORS = new CopyOnWriteArrayList<>();

    @Override
    public List<Author> getAll() {
        return AUTHORS.stream().filter(author -> author.getDeleted() == null || !author.getDeleted()).toList();
    }

    @Override
    public Optional<Author> findById(Integer id) {
        return AUTHORS.stream().filter(a -> a.getId().equals(id)).findFirst();
    }

    @Override
    public Author save(Author author) {
        Optional<Author> byId = findById(author.getId());
        if (byId.isPresent()) {
            AUTHORS.remove(byId.get());
        }
        AUTHORS.add(author);
        return author;
    }

    @Override
    public void delete(Author author) {
        author.setDeleted(true);
        save(author);
    }
}
