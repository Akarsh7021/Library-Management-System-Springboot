package ca.kpu.info2413.library.backend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface ManyAuthorHasManyPublicationRepository extends JpaRepository<ManyAuthorHasManyPublication, Integer> {

    @Query("SELECT m FROM ManyAuthorHasManyPublication m WHERE m.author_id_Author = :author_id_Author")
    List<ManyAuthorHasManyPublication> findByAuthor_id_Author(@Param("author_id_Author") int author_id_Author);

    @Query("SELECT m FROM ManyAuthorHasManyPublication m WHERE m.isbn_13_Publication = :isbn_13_Publication")
    List<ManyAuthorHasManyPublication> findByIsbn_13_Publication(@Param("isbn_13_Publication") int isbn_13_Publication);

}
