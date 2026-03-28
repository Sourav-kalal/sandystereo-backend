package com.sandystereo.backend.service.impl;

import com.sandystereo.backend.enums.PaymentType;
import com.sandystereo.backend.entity.Payment;
import com.sandystereo.backend.repository.PaymentRepository;
import com.sandystereo.backend.service.PaymentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository repository;
    public PaymentServiceImpl(PaymentRepository repository) { this.repository = repository; }

    @Override public List<Payment> findAll() { return repository.findAll(); }
    @Override public Payment findById(UUID id) { return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Payment not found")); }
    @Override public Payment save(Payment payment) { return repository.save(payment); }
    @Override public void deleteById(UUID id) { repository.deleteById(id); }

    @Override
    public List<Payment> getMonthlyPayments(int year, int month) {
        OffsetDateTime start = getStartOfMonth(year, month);
        OffsetDateTime end = getEndOfMonth(year, month);
        return repository.findByCreatedAtBetween(start, end);
    }

    @Override
    public Map<String, Object> getFinancialSummary(int year, int month) {
        OffsetDateTime start = getStartOfMonth(year, month);
        OffsetDateTime end = getEndOfMonth(year, month);
        List<Payment> payments = repository.findByCreatedAtBetween(start, end);

        BigDecimal totalRevenue = payments.stream()
                .filter(p -> p.getType() == PaymentType.REVENUE)
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalPayouts = payments.stream()
                .filter(p -> p.getType() == PaymentType.PAYOUT)
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalExpenses = payments.stream()
                .filter(p -> p.getType() == PaymentType.EXPENSE)
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal netProfit = totalRevenue.subtract(totalPayouts).subtract(totalExpenses);

        Map<String, Object> summary = new HashMap<>();
        summary.put("totalRevenue", totalRevenue);
        summary.put("totalPayouts", totalPayouts);
        summary.put("totalExpenses", totalExpenses);
        summary.put("netProfit", netProfit);
        summary.put("count", payments.size());
        
        return summary;
    }

    @Override
    public List<Payment> findByUserId(UUID userId) {
        com.sandystereo.backend.entity.User user = new com.sandystereo.backend.entity.User();
        user.setId(userId);
        return repository.findByUser(user);
    }

    private OffsetDateTime getStartOfMonth(int year, int month) {
        return LocalDateTime.of(year, month, 1, 0, 0).atOffset(ZoneOffset.UTC);
    }

    private OffsetDateTime getEndOfMonth(int year, int month) {
        LocalDateTime start = LocalDateTime.of(year, month, 1, 0, 0);
        return start.plusMonths(1).minusNanos(1).atOffset(ZoneOffset.UTC);
    }
}
