package com.codex.pcms.service;

import com.codex.pcms.model.Customer;
import com.codex.pcms.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> search(String keyword) {
        return customerRepository.findAll(keyword);
    }

    public Customer get(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found: " + id));
    }

    public Customer save(Customer customer) {
        long id = customerRepository.save(customer);
        return get(id);
    }

    public void update(Customer customer) {
        customerRepository.update(customer);
    }

    public void delete(Long id) {
        customerRepository.deleteById(id);
    }
}
