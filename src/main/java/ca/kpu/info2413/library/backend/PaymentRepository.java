package ca.kpu.info2413.library.backend;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    List<Payment> findByPaymentId(Integer paymentId);

}
