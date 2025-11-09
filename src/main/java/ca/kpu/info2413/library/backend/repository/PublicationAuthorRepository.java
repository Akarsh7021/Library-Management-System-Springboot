package ca.kpu.info2413.library.backend.repository;

import ca.kpu.info2413.library.backend.model.Author;
import ca.kpu.info2413.library.backend.model.Publication;
import ca.kpu.info2413.library.backend.model.PublicationAuthor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface PublicationAuthorRepository extends JpaRepository<PublicationAuthor, Integer>
{

    List<PublicationAuthor> findByAuthor(Author author);

    List<PublicationAuthor> findByPublication(Publication publication);

}
