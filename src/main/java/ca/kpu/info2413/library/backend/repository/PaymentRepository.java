package ca.kpu.info2413.library.backend.repository;

import ca.kpu.info2413.library.backend.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface PaymentRepository extends JpaRepository<Payment, Integer>
{

    List<Payment> findByPaymentId(Integer paymentId);

}
