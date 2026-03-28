package com.sandystereo.backend.service;

import com.sandystereo.backend.entity.Payment;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface PaymentService {
    List<Payment> findAll();
    Payment findById(UUID id);
    Payment save(Payment payment);
    void deleteById(UUID id);
    List<Payment> getMonthlyPayments(int year, int month);
    Map<String, Object> getFinancialSummary(int year, int month);
    List<Payment> findByUserId(UUID userId);
}
