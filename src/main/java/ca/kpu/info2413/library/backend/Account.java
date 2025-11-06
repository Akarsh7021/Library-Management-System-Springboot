package ca.kpu.info2413.library.backend;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int account_id;
    private String password_hash;
    private String account_type;
    private String notification_email;
    private String phone_number;
    private String full_name;

    // for JPA only, no use
    public Account() {
    }

    // getters, setters and constructor
}
