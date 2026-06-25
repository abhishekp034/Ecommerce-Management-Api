package com.codex.pcms.repository;

import com.codex.pcms.model.Product;
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
public class ProductRepository {
    private static final RowMapper<Product> PRODUCT_ROW_MAPPER = (rs, rowNum) -> {
        Product product = new Product();
        product.setId(rs.getLong("id"));
        product.setSku(rs.getString("sku"));
        product.setName(rs.getString("name"));
        product.setCategory(rs.getString("category"));
        product.setPrice(rs.getBigDecimal("price"));
        product.setQuantity(rs.getInt("quantity"));
        product.setStatus(rs.getString("status"));
        product.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return product;
    };

    private final JdbcTemplate jdbcTemplate;

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Product> findAll(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return jdbcTemplate.query("""
                    SELECT id, sku, name, category, price, quantity, status, created_at
                    FROM products
                    ORDER BY id DESC
                    """, PRODUCT_ROW_MAPPER);
        }
        String pattern = "%" + keyword.toLowerCase() + "%";
        return jdbcTemplate.query("""
                SELECT id, sku, name, category, price, quantity, status, created_at
                FROM products
                WHERE LOWER(name) LIKE ? OR LOWER(sku) LIKE ? OR LOWER(category) LIKE ?
                ORDER BY id DESC
                """, PRODUCT_ROW_MAPPER, pattern, pattern, pattern);
    }

    public List<Product> findLowStock() {
        return jdbcTemplate.query("""
                SELECT id, sku, name, category, price, quantity, status, created_at
                FROM products
                WHERE quantity < 20 OR status = 'LOW_STOCK'
                ORDER BY quantity ASC, id DESC
                LIMIT 12
                """, PRODUCT_ROW_MAPPER);
    }

    public Optional<Product> findById(Long id) {
        return jdbcTemplate.query("""
                SELECT id, sku, name, category, price, quantity, status, created_at
                FROM products
                WHERE id = ?
                """, PRODUCT_ROW_MAPPER, id).stream().findFirst();
    }

    public long save(Product product) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("""
                    INSERT INTO products (sku, name, category, price, quantity, status)
                    VALUES (?, ?, ?, ?, ?, ?)
                    """, new String[]{"id"});
            ps.setString(1, product.getSku());
            ps.setString(2, product.getName());
            ps.setString(3, product.getCategory());
            ps.setBigDecimal(4, product.getPrice());
            ps.setInt(5, product.getQuantity());
            ps.setString(6, product.getStatus());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public int update(Product product) {
        return jdbcTemplate.update("""
                UPDATE products
                SET sku = ?, name = ?, category = ?, price = ?, quantity = ?, status = ?
                WHERE id = ?
                """, product.getSku(), product.getName(), product.getCategory(), product.getPrice(),
                product.getQuantity(), product.getStatus(), product.getId());
    }

    public int deleteById(Long id) {
        return jdbcTemplate.update("DELETE FROM products WHERE id = ?", id);
    }

    public int count() {
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM products", Integer.class);
        return count == null ? 0 : count;
    }
}
