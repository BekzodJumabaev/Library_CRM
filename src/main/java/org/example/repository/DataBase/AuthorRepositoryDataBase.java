package org.example.repository.DataBase;

import org.example.model.Author;
import org.example.repository.AuthorRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AuthorRepositoryDataBase implements AuthorRepository {

    private final JdbcTemplate jdbcTemplate;

    public AuthorRepositoryDataBase(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Author> getAll() {
        String sql = """
                select a.*
                from author a
                 where a.deleted = false
                  order by a.full_name""";



        return jdbcTemplate.query(sql, getMapper());
    }

    @Override
    public Optional<Author> findById(Integer id) {
        String sql = """
                select a.* from author a
                where a.deleted = false
                and a.id = ?
        """;
        try {
            Author author = jdbcTemplate.queryForObject(sql, getMapper(), id);
            return Optional.ofNullable(author);
        }catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Author save(Author author) {
        if (author.getId() == null) {
            String sql = "INSERT INTO author (full_name, deleted) VALUES (?, ?)";
            jdbcTemplate.update(sql, author.getFullName(), author.getDeleted());
        } else {
            String sql = "UPDATE author SET full_name = ?, deleted = ? WHERE id = ?";

            jdbcTemplate.update(sql, author.getFullName(), author.getDeleted(), author.getId());
        }
        return author;
    }

    @Override
    public void delete(Author author) {
        author.setDeleted(true);
        save(author);
    }

    public RowMapper<Author> getMapper() {
        return  (rs, rowNum) -> {
            Author author = new Author();
            author.setId(Integer.valueOf(rs.getString("id")));
            author.setFullName(rs.getString("full_name"));
            author.setDeleted(rs.getBoolean("deleted"));
            return author;
        };
    }
}
