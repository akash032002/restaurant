package main.restaurantspringbootjdbc.dao;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO {

    private final JdbcTemplate jdbcTemplate;

    public UserDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean register(String username, String password) {
        String sql = "INSERT INTO users(username,password) VALUES(?,?)";
        return jdbcTemplate.update(sql, username, password) > 0;
    }

    public boolean login(String username, String password) {
        String sql = "SELECT COUNT(*) FROM users WHERE username=? AND password=?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, username, password);
        return count != null && count > 0;
    }

    public boolean existsByUsername(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username=?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, username);
        return count != null && count > 0;
    }
}