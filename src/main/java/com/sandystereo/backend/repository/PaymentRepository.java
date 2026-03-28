package com.sandystereo.backend.repository;

import com.sandystereo.backend.entity.Payment;
import com.sandystereo.backend.entity.User;
import com.sandystereo.backend.enums.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    List<Payment> findByCreatedAtBetween(OffsetDateTime start, OffsetDateTime end);
    List<Payment> findByTypeAndCreatedAtBetween(PaymentType type, OffsetDateTime start, OffsetDateTime end);
    List<Payment> findByUser(User user);
}
