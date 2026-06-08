package org.example.repository;

import org.example.model.Borrow;

import java.util.List;
import java.util.Optional;

public interface BorrowRepository {

    List<Borrow> getAll();

    Optional<Borrow> getById(Integer id);

    Borrow save(Borrow borrow);

    void delete(Borrow borrow);

    List<Borrow> findAllByUserId(Integer userId);
}
