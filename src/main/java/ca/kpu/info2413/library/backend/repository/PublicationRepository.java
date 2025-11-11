package ca.kpu.info2413.library.backend.repository;

import ca.kpu.info2413.library.backend.model.Publication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface PublicationRepository extends JpaRepository<Publication, Integer>
{

    List<Publication> findByIsbn13(Integer isbn13);

    List<Publication> findByTitleContainsIgnoreCase(String title);

    List<Publication> findByGenre(String genre);

}
