package org.example.repository.InMemory;

import org.example.model.Borrow;
import org.example.repository.BorrowRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class BorrowRepositoryDB implements BorrowRepository {

    private List<Borrow> BORROW = new CopyOnWriteArrayList<>();


    public List<Borrow> getAll() {
        return BORROW;
    }

    @Override
    public Optional<Borrow> getById(Integer id) {
        return BORROW.stream().filter(borrow -> borrow.getId().equals(id)).findFirst();
    }

    public Borrow save(Borrow borrow) {
        Optional<Borrow> byId = getById(borrow.getId());
        byId.ifPresent(BORROW::remove);
        BORROW.add(borrow);
        return borrow;
    }

    @Override
    public void delete(Borrow borrow) {
        borrow.setDeleted(true);
        BORROW.add(borrow);
    }

    @Override
    public List<Borrow> findAllByUserId(Integer userId) {
        return List.of();
    }
}
