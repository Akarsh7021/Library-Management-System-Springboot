package ca.kpu.info2413.library.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Configuration")
@Data
@NoArgsConstructor
public class Configuration
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "config_key")
    private String configKey;

    @Column(name = "config_value")
    private String configValue;

}
