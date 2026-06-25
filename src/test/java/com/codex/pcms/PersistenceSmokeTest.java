package com.codex.pcms;

import com.codex.pcms.model.Customer;
import com.codex.pcms.model.Product;
import com.codex.pcms.repository.CustomerRepository;
import com.codex.pcms.repository.DashboardRepository;
import com.codex.pcms.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PersistenceSmokeTest {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private DashboardRepository dashboardRepository;

    @Test
    void loadsSeedDataAndDashboardAggregates() {
        assertThat(productRepository.count()).isGreaterThanOrEqualTo(220);
        assertThat(customerRepository.findAll(null)).hasSizeGreaterThanOrEqualTo(8);
        assertThat(dashboardRepository.loadStats().inventoryValue()).isGreaterThan(BigDecimal.ZERO);
    }

    @Test
    void createsProductAndCustomerThroughJdbcRepositories() {
        Product product = new Product();
        product.setSku("SKU-TEST-001");
        product.setName("Test Docking Station");
        product.setCategory("Connectivity");
        product.setPrice(BigDecimal.valueOf(3499.00));
        product.setQuantity(12);
        product.setStatus("ACTIVE");

        long productId = productRepository.save(product);
        assertThat(productRepository.findById(productId)).isPresent();

        Customer customer = new Customer();
        customer.setName("Test Customer");
        customer.setEmail("test.customer@example.com");
        customer.setPhone("9876500099");
        customer.setCity("Jaipur");
        customer.setLoyaltyTier("SILVER");

        long customerId = customerRepository.save(customer);
        assertThat(customerRepository.findById(customerId)).isPresent();
    }
}
