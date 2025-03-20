package com.example.thimd4view.service;

import com.example.thimd4view.model.Transaction;

import java.util.List;

public interface ITransactionService extends IGenerateService<Transaction> {
    List<Transaction> searchByCustomerNameOrServiceType(String keyword);
}
