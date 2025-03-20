package com.example.thimd4view.service;

import com.example.thimd4view.model.Transaction;
import com.example.thimd4view.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService implements ITransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    @Override
    public Transaction findById(Long id) {
        return transactionRepository.findById(id).orElse(null);
    }

    @Override
    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public void delete(Transaction transaction) {
        transactionRepository.delete(transaction);
    }

    @Override
    public List<Transaction> searchByCustomerNameOrServiceType(String keyword) {
        return transactionRepository.findAll().stream()
                .filter(t -> t.getCustomer().getName().toLowerCase().contains(keyword.toLowerCase()) ||
                        t.getServiceType().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public Page<Transaction> searchByCustomerNameAndServiceType(String customerName, String serviceType, Pageable pageable) {
        return transactionRepository.searchByCustomerNameAndServiceType(customerName, serviceType, pageable);
    }
}
