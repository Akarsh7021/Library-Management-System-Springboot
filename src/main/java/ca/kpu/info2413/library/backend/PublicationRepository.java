package ca.kpu.info2413.library.backend;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface PublicationRepository extends JpaRepository<Publication, Integer>
{

    List<Publication> findByIsbn13(Integer isbn13);

}
