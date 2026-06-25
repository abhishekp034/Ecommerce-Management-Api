package com.codex.pcms.controller;

import com.codex.pcms.model.Product;
import com.codex.pcms.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String list(@RequestParam(required = false) String keyword, Model model) {
        model.addAttribute("products", productService.search(keyword));
        model.addAttribute("keyword", keyword == null ? "" : keyword);
        return "products/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        Product product = new Product();
        product.setStatus("ACTIVE");
        product.setPrice(BigDecimal.valueOf(999));
        model.addAttribute("product", product);
        return "products/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute Product product, BindingResult result) {
        if (result.hasErrors()) {
            return "products/form";
        }
        productService.save(product);
        return "redirect:/products";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.get(id));
        return "products/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute Product product, BindingResult result) {
        product.setId(id);
        if (result.hasErrors()) {
            return "products/form";
        }
        productService.update(product);
        return "redirect:/products";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        productService.delete(id);
        return "redirect:/products";
    }
}
