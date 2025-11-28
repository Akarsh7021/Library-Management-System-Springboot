package ca.kpu.info2413.library.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LibraryApplicationSpring
{
    public static void main(String[] args)
    {
        SpringApplication.run(LibraryApplicationSpring.class, args);
    }
}
