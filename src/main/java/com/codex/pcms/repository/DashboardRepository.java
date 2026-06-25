package com.codex.pcms.repository;

import com.codex.pcms.model.DashboardStats;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public class DashboardRepository {
    private final JdbcTemplate jdbcTemplate;

    public DashboardRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public DashboardStats loadStats() {
        return jdbcTemplate.queryForObject("""
                SELECT
                    (SELECT COUNT(*) FROM products) AS total_products,
                    (SELECT COUNT(*) FROM products WHERE status = 'ACTIVE') AS active_products,
                    (SELECT COUNT(*) FROM products WHERE quantity < 20 OR status = 'LOW_STOCK') AS low_stock_products,
                    (SELECT COUNT(*) FROM customers) AS total_customers,
                    (SELECT COUNT(*) FROM customer_orders) AS total_orders,
                    (SELECT COALESCE(SUM(price * quantity), 0) FROM products) AS inventory_value,
                    (SELECT COALESCE(SUM(p.price * o.quantity), 0)
                       FROM customer_orders o
                       JOIN products p ON p.id = o.product_id
                      WHERE o.order_status <> 'CANCELLED') AS booked_revenue
                """, (rs, rowNum) -> new DashboardStats(
                rs.getInt("total_products"),
                rs.getInt("active_products"),
                rs.getInt("low_stock_products"),
                rs.getInt("total_customers"),
                rs.getInt("total_orders"),
                rs.getBigDecimal("inventory_value") == null ? BigDecimal.ZERO : rs.getBigDecimal("inventory_value"),
                rs.getBigDecimal("booked_revenue") == null ? BigDecimal.ZERO : rs.getBigDecimal("booked_revenue")
        ));
    }
}
