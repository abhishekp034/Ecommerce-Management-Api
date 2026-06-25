package com.codex.pcms.repository;

import com.codex.pcms.model.CustomerOrder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderRepository {
    private static final RowMapper<CustomerOrder> ORDER_ROW_MAPPER = (rs, rowNum) -> {
        CustomerOrder order = new CustomerOrder();
        order.setId(rs.getLong("id"));
        order.setCustomerId(rs.getLong("customer_id"));
        order.setProductId(rs.getLong("product_id"));
        order.setQuantity(rs.getInt("quantity"));
        order.setOrderStatus(rs.getString("order_status"));
        order.setOrderDate(rs.getDate("order_date").toLocalDate());
        order.setCustomerName(rs.getString("customer_name"));
        order.setProductName(rs.getString("product_name"));
        order.setLineTotal(rs.getBigDecimal("line_total"));
        return order;
    };

    private final JdbcTemplate jdbcTemplate;

    public OrderRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<CustomerOrder> findAll() {
        return jdbcTemplate.query("""
                SELECT o.id, o.customer_id, o.product_id, o.quantity, o.order_status, o.order_date,
                       c.name AS customer_name, p.name AS product_name, (p.price * o.quantity) AS line_total
                FROM customer_orders o
                JOIN customers c ON c.id = o.customer_id
                JOIN products p ON p.id = o.product_id
                ORDER BY o.order_date DESC, o.id DESC
                """, ORDER_ROW_MAPPER);
    }

    public Optional<CustomerOrder> findById(Long id) {
        return jdbcTemplate.query("""
                SELECT o.id, o.customer_id, o.product_id, o.quantity, o.order_status, o.order_date,
                       c.name AS customer_name, p.name AS product_name, (p.price * o.quantity) AS line_total
                FROM customer_orders o
                JOIN customers c ON c.id = o.customer_id
                JOIN products p ON p.id = o.product_id
                WHERE o.id = ?
                """, ORDER_ROW_MAPPER, id).stream().findFirst();
    }

    public long save(CustomerOrder order) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("""
                    INSERT INTO customer_orders (customer_id, product_id, quantity, order_status, order_date)
                    VALUES (?, ?, ?, ?, ?)
                    """, new String[]{"id"});
            ps.setLong(1, order.getCustomerId());
            ps.setLong(2, order.getProductId());
            ps.setInt(3, order.getQuantity());
            ps.setString(4, order.getOrderStatus());
            ps.setDate(5, Date.valueOf(order.getOrderDate()));
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public int update(CustomerOrder order) {
        return jdbcTemplate.update("""
                UPDATE customer_orders
                SET customer_id = ?, product_id = ?, quantity = ?, order_status = ?, order_date = ?
                WHERE id = ?
                """, order.getCustomerId(), order.getProductId(), order.getQuantity(), order.getOrderStatus(),
                order.getOrderDate(), order.getId());
    }

    public int deleteById(Long id) {
        return jdbcTemplate.update("DELETE FROM customer_orders WHERE id = ?", id);
    }
}
