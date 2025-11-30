package ca.kpu.info2413.library.backend.service;

import ca.kpu.info2413.library.backend.model.Configuration;
import ca.kpu.info2413.library.backend.repository.ConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConfigurationService
{
    @Autowired
    private ConfigurationRepository configurationRepository;

    public Configuration save(Configuration configuration) {return configurationRepository.save(configuration);}

    public List<Configuration> findAll() {return configurationRepository.findAll();}

    public Optional<Configuration> getByKey(String key) {return configurationRepository.findById(key);}

    public Configuration updateValue(String key, String value)
    {
        Configuration config = configurationRepository.findById(key).orElse(null);

        //if (config == null) return null;
        if (config == null)
        {
            config = new Configuration();
            config.setConfigKey(key);
        }

        config.setConfigValue(value);

        return configurationRepository.save(config);
    }

    public void deleteByKey(String key) {configurationRepository.deleteById(key);}
}
