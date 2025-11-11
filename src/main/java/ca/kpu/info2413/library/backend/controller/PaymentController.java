package ca.kpu.info2413.library.backend.controller;

import ca.kpu.info2413.library.backend.model.Account;
import ca.kpu.info2413.library.backend.model.Payment;
import ca.kpu.info2413.library.backend.service.AccountService;
import ca.kpu.info2413.library.backend.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController
{

    @Autowired
    private PaymentService paymentService;

    @GetMapping
    public List<Payment> findAll()
    {
        return paymentService.findAll();
    }

    @GetMapping("/{payment_id}")
    public List<Payment> findByPaymentId(@PathVariable Integer payment_id)
    {
        return paymentService.findByPaymentId(payment_id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Payment create(@RequestBody Payment payment)
    {
        return paymentService.save(payment);
    }

    @PutMapping
    public Payment update(@RequestBody Payment payment)
    {
        return paymentService.save(payment);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{payment_id}")
    public void deleteByPaymentId(@PathVariable Integer payment_id)
    {
        paymentService.deleteByPaymentId(payment_id);
    }

    /// ///


    @GetMapping("/find/payment_id/{payment_id}")
    public List<Payment> findByPaymentId(@PathVariable Integer payment_id)
    {
        return paymentService.findByPaymentId(payment_id);
    }

    @GetMapping("/find/account_id_account/{account_id_account}")
    public List<Payment> findByAccounIdAccount(@PathVariable Integer account_id_account)
    {
        return paymentService.findByAccountIdAccount(account_id_account);
    }

    @GetMapping("/find/processed_by/{processed_by}")
    public List<Payment> findByProcessedBy(@PathVariable String processed_by)
    {
        return paymentService.findByProcessedBy(processed_by);
    }
}