package org.example.repository.DataBase;

import org.example.model.Borrow;
import org.example.repository.BorrowRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class BorrowRepositoryDataBase implements BorrowRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public BorrowRepositoryDataBase(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Borrow> getAll() {
        String sql = "select * from borrows where deleted=false order by id desc";
        return jdbcTemplate.query(sql, getMapper());
    }

    @Override
    public Optional<Borrow> getById(Integer id) {
        if (id == null) {
            return Optional.empty();
        }
        String sql = "select * from borrows where id = :id and deleted=false";

        try {
            Borrow borrow = jdbcTemplate.queryForObject(sql, Map.of("id", id), getMapper());
            return Optional.of(borrow);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Borrow save(Borrow borrow) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("user_id", borrow.getUserId());
        params.addValue("book_id", borrow.getBookId());
        params.addValue("borrow_date", Timestamp.valueOf(borrow.getBorrowDate()));
        params.addValue("deadline", Timestamp.valueOf(borrow.getDeadline()));
        params.addValue("return_date", borrow.getReturnDate() != null ? Timestamp.valueOf(borrow.getReturnDate()) : null);
        params.addValue("deleted", borrow.getDeleted());

        if (borrow.getId() == null) {
            String sql =
                    """
        
                            Insert into borrows (user_id, book_id, borrow_date, deadline, deleted)
        values (:user_id, :book_id, :borrow_date, :deadline, :deleted)
        """;
            jdbcTemplate.update(sql, params);
        }
        else {
            params.addValue("id", borrow.getId());
            String sql =
                            """
                    update borrows set
                                     user_id = :user_id,
                                     book_id = :book_id,
                                     return_date = :return_date,
                                     deleted = :deleted
                                     where id = :id
                                     """;

            jdbcTemplate.update(sql, params);
        }
        return borrow;
    }

    @Override
    public void delete(Borrow borrow) {
        String sql =
                """
                update borrows set deleted=true where
                id = :id;
        """;
        jdbcTemplate.update(sql, Map.of("id", borrow.getId()));
    }

    @Override
    public List<Borrow> findAllByUserId(Integer userId) {
        if (userId == null) {
            return List.of();
        }
        String sql = "select * from borrows where user_id = :user_id and deleted=false order by id desc";
       return jdbcTemplate.query(sql, Map.of("user_id", userId), getMapper());
    }

    private RowMapper<Borrow> getMapper() {
        return  (rs, rowNum) ->  {
            Borrow borrow = Borrow.builder()
                    .userId(rs.getInt("user_id"))
                    .bookId(rs.getInt("book_id"))
                    .borrowDate(rs.getTimestamp("borrow_date").toLocalDateTime())
                    .deadline(rs.getTimestamp("deadline").toLocalDateTime()).
                    build();

            borrow.setId(rs.getInt("id"));
            borrow.setDeleted(rs.getBoolean("deleted"));
            if (rs.getTimestamp("return_date") != null) {
                borrow.setReturnDate(rs.getTimestamp("return_date").toLocalDateTime());
            }
            return borrow;
        };
    }
}
