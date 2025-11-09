package ca.kpu.info2413.library.backend;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface PublicationAuthorRepository extends JpaRepository<PublicationAuthor, Integer> {

    List<PublicationAuthor> findByAuthorIdAuthor(Integer authorIdAuthor);

    List<PublicationAuthor> findByIsbn13Publication(Integer isbn13Publication);

}
