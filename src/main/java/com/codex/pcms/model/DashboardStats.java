package com.codex.pcms.model;

import java.math.BigDecimal;

public record DashboardStats(
        int totalProducts,
        int activeProducts,
        int lowStockProducts,
        int totalCustomers,
        int totalOrders,
        BigDecimal inventoryValue,
        BigDecimal bookedRevenue
) {
}
