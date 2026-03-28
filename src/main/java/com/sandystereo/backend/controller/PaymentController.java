package com.sandystereo.backend.controller;

import com.sandystereo.backend.entity.Payment;
import com.sandystereo.backend.service.PaymentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.sandystereo.backend.service.PaymentService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService service;
    public PaymentController(PaymentService service){this.service=service;}
    @GetMapping @PreAuthorize("hasAnyRole('admin','instructor')") public List<Payment> list(){return service.findAll();}
    @GetMapping("/{id}") @PreAuthorize("hasAnyRole('admin','instructor')") public Payment get(@PathVariable UUID id){return service.findById(id);}
    @PostMapping @PreAuthorize("isAuthenticated()") public Payment create(@RequestBody Payment e){return service.save(e);}    
    @PutMapping("/{id}") @PreAuthorize("hasRole('admin')") public Payment update(@PathVariable UUID id,@RequestBody Payment e){e.setId(id);return service.save(e);}    
    @DeleteMapping("/{id}") @PreAuthorize("hasRole('admin')") public void delete(@PathVariable UUID id){service.deleteById(id);}    

    @GetMapping("/summary")
    @PreAuthorize("hasRole('admin')")
    public Map<String, Object> getSummary(@RequestParam int year, @RequestParam int month) {
        return service.getFinancialSummary(year, month);
    }

    @GetMapping("/monthly")
    @PreAuthorize("hasRole('admin')")
    public List<Payment> getMonthly(@RequestParam int year, @RequestParam int month) {
        return service.getMonthlyPayments(year, month);
    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('student')")
    public List<Payment> getMyPayments(org.springframework.security.core.Authentication authentication) {
        return service.findByUserId(UUID.fromString(authentication.getName()));
    }
}
