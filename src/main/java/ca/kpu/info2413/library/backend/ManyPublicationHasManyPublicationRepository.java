package ca.kpu.info2413.library.backend;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface ManyPublicationHasManyPublicationRepository extends JpaRepository<ManyPublicationHasManyPublication, Integer> {

    @Query("SELECT m FROM ManyPublicationHasManyPublication m WHERE m.isbn_13_publication = :isbn_13_publication")
    List<ManyPublicationHasManyPublication> findByIsbn_13_publication(@Param("isbn_13_publication") int isbn_13_publication);

    @Query("SELECT m FROM ManyPublicationHasManyPublication m WHERE m.isbn_13_publication1 = :isbn_13_publication1")
    List<ManyPublicationHasManyPublication> findByIsbn_13_publication1(@Param("isbn_13_publication1") int isbn_13_publication1);

}