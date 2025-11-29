package ca.kpu.info2413.library.backend.repository;

import ca.kpu.info2413.library.backend.model.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfigurationRepository extends JpaRepository<Configuration, String>
{
}
