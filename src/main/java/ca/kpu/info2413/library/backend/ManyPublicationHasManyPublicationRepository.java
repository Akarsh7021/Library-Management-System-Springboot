package ca.kpu.info2413.library.backend;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface ManyPublicationHasManyPublicationRepository extends JpaRepository<ManyPublicationHasManyPublication, Integer> {

    List<ManyPublicationHasManyPublication> findByIsbn13Publication(Integer isbn13Publication);

    List<ManyPublicationHasManyPublication> findByIsbn13Publication1(Integer isbn13Publication1);

}