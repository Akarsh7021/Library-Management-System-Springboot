package ca.kpu.info2413.library.backend;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface PublicationPublicationRepository extends JpaRepository<PublicationPublication, Integer> {

    List<PublicationPublication> findByIsbn13Publication(Integer isbn13Publication);

    List<PublicationPublication> findByIsbn13Publication1(Integer isbn13Publication1);

}