package ca.kpu.info2413.library.backend;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface AuthorPublicationRepository extends JpaRepository<AuthorPublication, Integer> {

    List<AuthorPublication> findByAuthorIdAuthor(Integer authorIdAuthor);

    List<AuthorPublication> findByIsbn13Publication(Integer isbn13Publication);

}
