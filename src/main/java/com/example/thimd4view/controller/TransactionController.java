package com.example.thimd4view.controller;

import com.example.thimd4view.model.Customer;
import com.example.thimd4view.model.Transaction;
import com.example.thimd4view.service.CustomerService;
import com.example.thimd4view.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

    // Yêu cầu 1: Hiển thị danh sách giao dịch (không phân trang) và hỗ trợ tìm kiếm
    @GetMapping
    public String getTransactionList(
            @RequestParam(defaultValue = "") String keyword,
            Model model) {
        List<Transaction> transactions;

        if (keyword.isEmpty()) {
            transactions = transactionService.findAll();
        } else {
            transactions = transactionService.searchByCustomerNameOrServiceType(keyword);
        }

        model.addAttribute("transactions", transactions);
        model.addAttribute("keyword", keyword);
        return "transaction-list";
    }

    // Yêu cầu 2: Hiển thị form thêm mới giao dịch
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        List<Customer> customers = customerService.findAll();
        model.addAttribute("transaction", new Transaction());
        model.addAttribute("customers", customers);
        return "transaction-form";
    }

    // Yêu cầu 2: Xử lý thêm mới giao dịch
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