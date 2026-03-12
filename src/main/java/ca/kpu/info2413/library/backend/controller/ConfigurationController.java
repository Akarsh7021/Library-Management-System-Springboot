package ca.kpu.info2413.library.backend.controller;

import ca.kpu.info2413.library.backend.model.Configuration;
import ca.kpu.info2413.library.backend.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/config")
public class ConfigurationController
{
    @Autowired
    private ConfigurationService configurationService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Configuration create(@RequestBody Configuration config)
    {
        return configurationService.save(config);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Configuration> findAll()
    {
        return configurationService.findAll();
    }

    @GetMapping("/{config_key}")
    public ResponseEntity<?> getValueByKey(@PathVariable String config_key)
    {
        Optional<Configuration> config = configurationService.getByKey(config_key);
        if (config.isPresent()) return ResponseEntity.ok(config.get());
        else return ResponseEntity.badRequest().body("Cannot load configuration or configuration not found");
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Configuration update(@RequestBody Configuration config)
    {
        return configurationService.updateValue(config.getConfigKey(),config.getConfigValue());
    }

    @DeleteMapping({"/{config_key}"})
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteByKey(@PathVariable String config_key)
    {
        configurationService.deleteByKey(config_key);
    }
}
