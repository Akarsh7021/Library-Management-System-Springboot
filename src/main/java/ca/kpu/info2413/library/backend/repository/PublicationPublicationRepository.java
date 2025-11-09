package ca.kpu.info2413.library.backend.repository;

import ca.kpu.info2413.library.backend.model.Publication;
import ca.kpu.info2413.library.backend.model.PublicationPublication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface PublicationPublicationRepository extends JpaRepository<PublicationPublication, Integer>
{

    List<PublicationPublication> findByPublication(Publication publication);

    List<PublicationPublication> findByPublication1(Publication publication1);

}