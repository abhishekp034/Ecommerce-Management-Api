package com.codex.pcms.repository;

import com.codex.pcms.model.Customer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class CustomerRepository {
    private static final RowMapper<Customer> CUSTOMER_ROW_MAPPER = (rs, rowNum) -> {
        Customer customer = new Customer();
        customer.setId(rs.getLong("id"));
        customer.setName(rs.getString("name"));
        customer.setEmail(rs.getString("email"));
        customer.setPhone(rs.getString("phone"));
        customer.setCity(rs.getString("city"));
        customer.setLoyaltyTier(rs.getString("loyalty_tier"));
        customer.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return customer;
    };

    private final JdbcTemplate jdbcTemplate;

    public CustomerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Customer> findAll(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return jdbcTemplate.query("""
                    SELECT id, name, email, phone, city, loyalty_tier, created_at
                    FROM customers
                    ORDER BY id DESC
                    """, CUSTOMER_ROW_MAPPER);
        }
        String pattern = "%" + keyword.toLowerCase() + "%";
        return jdbcTemplate.query("""
                SELECT id, name, email, phone, city, loyalty_tier, created_at
                FROM customers
                WHERE LOWER(name) LIKE ? OR LOWER(email) LIKE ? OR LOWER(city) LIKE ?
                ORDER BY id DESC
                """, CUSTOMER_ROW_MAPPER, pattern, pattern, pattern);
    }

    public Optional<Customer> findById(Long id) {
        return jdbcTemplate.query("""
                SELECT id, name, email, phone, city, loyalty_tier, created_at
                FROM customers
                WHERE id = ?
                """, CUSTOMER_ROW_MAPPER, id).stream().findFirst();
    }

    public long save(Customer customer) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("""
                    INSERT INTO customers (name, email, phone, city, loyalty_tier)
                    VALUES (?, ?, ?, ?, ?)
                    """, new String[]{"id"});
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getEmail());
            ps.setString(3, customer.getPhone());
            ps.setString(4, customer.getCity());
            ps.setString(5, customer.getLoyaltyTier());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public int update(Customer customer) {
        return jdbcTemplate.update("""
                UPDATE customers
                SET name = ?, email = ?, phone = ?, city = ?, loyalty_tier = ?
                WHERE id = ?
                """, customer.getName(), customer.getEmail(), customer.getPhone(), customer.getCity(),
                customer.getLoyaltyTier(), customer.getId());
    }

    public int deleteById(Long id) {
        return jdbcTemplate.update("DELETE FROM customers WHERE id = ?", id);
    }
}
