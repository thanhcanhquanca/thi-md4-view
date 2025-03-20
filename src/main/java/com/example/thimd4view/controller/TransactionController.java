package com.example.thimd4view.controller;

import com.example.thimd4view.model.Customer;
import com.example.thimd4view.model.Transaction;
import com.example.thimd4view.service.CustomerService;
import com.example.thimd4view.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public String getTransactionList(
            @RequestParam(defaultValue = "") String customerName,
            @RequestParam(defaultValue = "") String serviceType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Transaction> transactionPage;

        if (customerName.isEmpty() && serviceType.isEmpty()) {
            transactionPage = transactionService.searchByCustomerNameAndServiceType(null, null, pageable);
        } else {
            transactionPage = transactionService.searchByCustomerNameAndServiceType(customerName, serviceType, pageable);
        }

        model.addAttribute("transactions", transactionPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", transactionPage.getTotalPages());
        model.addAttribute("customerName", customerName);
        model.addAttribute("serviceType", serviceType);
        return "transaction-list";
    }


    @GetMapping("/new")
    public String showCreateForm(Model model) {
        List<Customer> customers = customerService.findAll();
        model.addAttribute("transaction", new Transaction());
        model.addAttribute("customers", customers);
        return "transaction-form";
    }


    @PostMapping
    public String createTransaction(@Valid @ModelAttribute Transaction transaction, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<Customer> customers = customerService.findAll();
            model.addAttribute("customers", customers);
            return "transaction-form";
        }
        transactionService.save(transaction);
        model.addAttribute("successMessage", "Thêm giao dịch thành công!");
        return "redirect:/transactions";
    }


    @GetMapping("/detail/{id}")
    public String showTransactionDetail(@PathVariable Long id, Model model) {
        Transaction transaction = transactionService.findById(id);
        if (transaction == null) {
            return "redirect:/transactions";
        }
        model.addAttribute("transaction", transaction);
        return "transaction-detail";
    }

    @GetMapping("/delete/{id}")
    public String deleteTransaction(@PathVariable Long id) {
        Transaction transaction = transactionService.findById(id);
        if (transaction != null) {
            transactionService.delete(transaction);
        }
        return "redirect:/transactions";
    }
}