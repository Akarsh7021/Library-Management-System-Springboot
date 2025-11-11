package ca.kpu.info2413.library.backend.service;

import ca.kpu.info2413.library.backend.model.*;
import ca.kpu.info2413.library.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService
{
    @Autowired
    PaymentRepository paymentRepository;

    public List<Payment> findAll()
    {
        return paymentRepository.findAll();
    }

    public List<Payment> findByPaymentId(Integer paymentId)
    {
        return paymentRepository.findByPaymentId(paymentId);
    }

    public Payment save(Payment payment)
    {
        return paymentRepository.save(payment);
    }

    public void deleteByPaymentId(Integer paymentId)
    {
        paymentRepository.deleteById(paymentId);
    }

    /// ////////////////////


    //redo using service later

    public Optional<Payment> findByPaymentId(Integer paymentId)
    {
        return paymentRepository.findByPaymentId(paymentId);
    }

    public Optional<Payment> findByAccountIdAccount(Integer account_id_account)
    {
        return paymentRepository.findByAccountIdAccount(account_id_account);
    }

    public List<Payment> findByProcessedBy(String processedBy)
    {
        return paymentRepository.findByProcessedBy(processedBy);
    }

}
