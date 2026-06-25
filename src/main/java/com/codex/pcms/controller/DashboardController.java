package com.codex.pcms.controller;

import com.codex.pcms.service.DashboardService;
import com.codex.pcms.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
    private final DashboardService dashboardService;
    private final ProductService productService;

    public DashboardController(DashboardService dashboardService, ProductService productService) {
        this.dashboardService = dashboardService;
        this.productService = productService;
    }

    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("stats", dashboardService.stats());
        model.addAttribute("lowStockProducts", productService.lowStock());
        return "index";
    }
}
