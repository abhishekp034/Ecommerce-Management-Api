package com.codex.pcms.service;

import com.codex.pcms.model.DashboardStats;
import com.codex.pcms.repository.DashboardRepository;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {
    private final DashboardRepository dashboardRepository;

    public DashboardService(DashboardRepository dashboardRepository) {
        this.dashboardRepository = dashboardRepository;
    }

    public DashboardStats stats() {
        return dashboardRepository.loadStats();
    }
}
