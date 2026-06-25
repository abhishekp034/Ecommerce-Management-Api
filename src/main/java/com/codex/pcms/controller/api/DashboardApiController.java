package com.codex.pcms.controller.api;

import com.codex.pcms.model.DashboardStats;
import com.codex.pcms.model.Product;
import com.codex.pcms.service.DashboardService;
import com.codex.pcms.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardApiController {
    private final DashboardService dashboardService;
    private final ProductService productService;

    public DashboardApiController(DashboardService dashboardService, ProductService productService) {
        this.dashboardService = dashboardService;
        this.productService = productService;
    }

    @GetMapping("/stats")
    public DashboardStats stats() {
        return dashboardService.stats();
    }

    @GetMapping("/low-stock")
    public List<Product> lowStockProducts() {
        return productService.lowStock();
    }
}