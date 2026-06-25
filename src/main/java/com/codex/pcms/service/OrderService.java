package com.codex.pcms.service;

import com.codex.pcms.model.CustomerOrder;
import com.codex.pcms.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<CustomerOrder> findAll() {
        return orderRepository.findAll();
    }

    public CustomerOrder get(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + id));
    }

    public CustomerOrder newOrder() {
        CustomerOrder order = new CustomerOrder();
        order.setQuantity(1);
        order.setOrderStatus("PLACED");
        order.setOrderDate(LocalDate.now());
        return order;
    }

    public CustomerOrder save(CustomerOrder order) {
        long id = orderRepository.save(order);
        return get(id);
    }

    public void update(CustomerOrder order) {
        orderRepository.update(order);
    }

    public void delete(Long id) {
        orderRepository.deleteById(id);
    }
}
