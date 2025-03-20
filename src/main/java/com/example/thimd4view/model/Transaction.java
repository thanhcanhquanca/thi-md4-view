package com.example.thimd4view.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "giao_dich")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Mã giao dịch không được để trống")
    @Pattern(regexp = "^MGD-\\d{4}$", message = "Mã giao dịch phải có định dạng MGD-XXXX (XXXX là 4 chữ số)")
    private String transactionCode;

    @NotNull(message = "Khách hàng không được để trống")
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @NotBlank(message = "Loại dịch vụ không được để trống")
    private String serviceType;

    @NotNull(message = "Ngày giao dịch không được để trống")
    @Future(message = "Ngày giao dịch phải là ngày trong tương lai")
    private LocalDate transactionDate;

    @NotNull(message = "Đơn giá không được để trống")
    @DecimalMin(value = "500000.0", message = "Đơn giá phải lớn hơn 500,000 VND/m²")
    private Double price;

    @NotNull(message = "Diện tích không được để trống")
    @DecimalMin(value = "20.0", message = "Diện tích phải lớn hơn 20 m²")
    private Double area;
}