package ca.kpu.info2413.library.backend;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface ManyAuthorHasManyPublicationRepository extends JpaRepository<ManyAuthorHasManyPublication, Integer> {

    List<ManyAuthorHasManyPublication> findByAuthorIdAuthor(Integer authorIdAuthor);

    List<ManyAuthorHasManyPublication> findByIsbn13Publication(Integer isbn13Publication);

}
