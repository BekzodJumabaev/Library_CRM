package org.example.repository.DataBase;

import org.example.model.AuthUser;
import org.example.model.enums.AuthRole;
import org.example.repository.UserRepository;
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
public class UserRepositoryDataBase implements UserRepository{

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UserRepositoryDataBase(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<AuthUser> getAllUsers() {
        String sql = "SELECT * FROM users where deleted = false ORDER BY created_at DESC";
        List<AuthUser> query = jdbcTemplate.query(sql, getMapper());
        return query;
    }

    @Override
    public Optional<AuthUser> getUserById(Integer id) {
        String sql = "SELECT * FROM users WHERE id = :id and deleted = false LIMIT 1";

        try {
            AuthUser authUser = jdbcTemplate.queryForObject(sql, Map.of("id", id), getMapper());
            return Optional.ofNullable(authUser);
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }    }

    @Override
    public AuthUser saveUser(AuthUser user) {

        boolean authUser = user.getId() == null;

        String sql = authUser ?
                """
                insert into users (full_name,  username, password, phone, role, created_at, deleted)
                values (:full_name, :username, :password, :phone, :role, :created_at, :deleted)
                """ :
                """
                update users set full_name = :full_name, username = :username, 
                  phone = :phone, password = :password, role = :role, updated_at = :updated_at
                where id = :id
                """;

        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("id", user.getId());
        params.addValue("full_name", user.getFullName());
        params.addValue("username", user.getUsername());
        params.addValue("password", user.getPassword());
        params.addValue("phone", user.getPhone());
        params.addValue("role", user.getRole().name());
        params.addValue("created_at", Timestamp.valueOf(user.getCreatedAt()));
        params.addValue("updated_at", user.getUpdatedAt() != null ? Timestamp.valueOf(user.getUpdatedAt()) : null);
        params.addValue("deleted",  user.getDeleted());

        jdbcTemplate.update(sql, params);
        return user;
    }

    @Override
    public void deleteUser(AuthUser user) {
        String sql = "DELETE FROM users WHERE id = :id";
        jdbcTemplate.update(sql, Map.of("id", user.getId()));
    }

    public RowMapper<AuthUser> getMapper() {
        return  (rs, rowNum) -> {
            AuthUser user = AuthUser .builder()
                    .fullName(rs.getString("full_name"))
                    .username(rs.getString("username"))
                    .password(rs.getString("password"))
                    .phone(rs.getString("phone"))
                    .role(AuthRole.valueOf(rs.getString("role")))
                    .build();
            user.setId(rs.getInt("id"));
            user.setDeleted(rs.getBoolean("deleted"));

            if (rs.getTimestamp("created_at") != null) {
                user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            }
            if (rs.getTimestamp("updated_at") != null) {
                user.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
            }
            return user;
        };
    }
}
