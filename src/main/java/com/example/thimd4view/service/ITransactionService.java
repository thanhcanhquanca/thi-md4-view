package com.example.thimd4view.service;

import com.example.thimd4view.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ITransactionService extends IGenerateService<Transaction> {
    List<Transaction> searchByCustomerNameOrServiceType(String keyword);
    Page<Transaction> searchByCustomerNameAndServiceType(String customerName, String serviceType, Pageable pageable);
}
