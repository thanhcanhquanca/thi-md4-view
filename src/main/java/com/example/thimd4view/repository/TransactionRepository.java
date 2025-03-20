package com.example.thimd4view.repository;

import com.example.thimd4view.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT t FROM Transaction t WHERE t.customer.name LIKE %:keyword% OR t.serviceType LIKE %:keyword%")
    Page<Transaction> searchByCustomerNameOrServiceType(String keyword, Pageable pageable);

    @Query("SELECT t FROM Transaction t WHERE " +
            "(:customerName IS NULL OR t.customer.name LIKE %:customerName%) AND " +
            "(:serviceType IS NULL OR t.serviceType LIKE %:serviceType%)")
    Page<Transaction> searchByCustomerNameAndServiceType(
            @Param("customerName") String customerName,
            @Param("serviceType") String serviceType,
            Pageable pageable);
}