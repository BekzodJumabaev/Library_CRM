package org.example.repository.DataBase;

import org.example.model.Book;
import org.example.repository.BookRepository;
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
public class BookRepositoryDataBase implements BookRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public BookRepositoryDataBase(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }


    @Override
    public List<Book> getAll() {
        String sql = """
                SELECT b.*, a.full_name as author_name FROM books b
                    left join author a 
                     on b.author_id=a.id
                        where b.deleted = false;""";
        return namedParameterJdbcTemplate.query(sql, getMapper());
    }

    @Override
    public Optional<Book> getById(Integer id) {
        String sql = """
                SELECT b.*, a.full_name as author_name 
                FROM books b
                 LEFT JOIN author a 
                 ON b.author_id = a.id 
                 WHERE b.deleted = false AND b.id = :id 
                """;

        try {
            Book book = namedParameterJdbcTemplate.queryForObject(sql, Map.of("id", id), getMapper());
            return Optional.ofNullable(book);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Book save(Book book) {

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", book.getId());
        params.addValue("title", book.getTitle());
        params.addValue("author_id", book.getAuthorId());
        params.addValue("image_url", book.getImageUrl());
        params.addValue("count", book.getCount());
        params.addValue("available", book.getAvailable());
        params.addValue("deleted", book.getDeleted());
        params.addValue("created_at", Timestamp.valueOf(book.getCreatedAt()));
        params.addValue("updated_at", book.getUpdatedAt() != null ? Timestamp.valueOf(book.getUpdatedAt()) : null);

        if (book.getId() == null) {
            String sql = """
                INSERT INTO books (title, author_id, image_url, count, available, deleted, created_at)
                VALUES (:title, :author_id, :image_url, :count, :available, :deleted, :created_at)
            """;
            namedParameterJdbcTemplate.update(sql, params);
        } else {
            String sql = """
                UPDATE books 
                SET title = :title, author_id = :author_id, image_url = :image_url, 
                    count = :count, available = :available, deleted = :deleted, updated_at = :updated_at
                WHERE id = :id
            """;
            namedParameterJdbcTemplate.update(sql, params);
        }
        return book;
    }

    @Override
    public void delete(Book book) {
        String sql = """
                        Update books set deleted = true where id = :id
                """;
        namedParameterJdbcTemplate.update(sql, Map.of("id", book.getId()));
    }

    @Override
    public void updateBookCount(Integer id, int i) {
        String sql = "update books set count = count + :i where id = :id";
        Map<String, Integer> map = Map.of("id", id, "i", i);
        namedParameterJdbcTemplate.update(sql, map);
    }

    private RowMapper<Book> getMapper() {
        return (rs, rowNum) -> {
            Book book = Book.builder()
                    .title(rs.getString("title"))
                    .authorId(rs.getInt("author_id"))
                    .authorName(rs.getString("author_name"))
                    .imageUrl(rs.getString("image_url"))
                    .count(rs.getInt("count"))
                    .available(rs.getBoolean("available"))
                    .build();

            book.setId(rs.getInt("id"));
            book.setDeleted(rs.getBoolean("deleted"));

            if (rs.getTimestamp("created_at") != null) {
                book.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            }
            if (rs.getTimestamp("updated_at") != null) {
                book.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
            }
            return book;
        };
    }
}
