package ca.kpu.info2413.library.backend.controller;

import ca.kpu.info2413.library.backend.model.Publication;
import ca.kpu.info2413.library.backend.service.PublicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publication")
public class PublicationController
{

    @Autowired
    private PublicationService publicationService;

    @GetMapping
    public List<Publication> findAll()
    {
        return publicationService.findAll();
    }

    @GetMapping("/{isbn13}")
    public List<Publication> findByIsbn13(@PathVariable Integer isbn13)
    {
        return publicationService.findByIsbn13(isbn13);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Publication create(@RequestBody Publication publication)
    {
        return publicationService.save(publication);
    }

    @PutMapping
    public Publication update(@RequestBody Publication publication)
    {
        return publicationService.save(publication);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{isbn13}")
    public void deleteById(@PathVariable Integer isbn13)
    {
        publicationService.deleteByIsbn13(isbn13);
    }

    /// ///


    @GetMapping("/find/title/{title}")
    public List<Publication> findByTitle(@PathVariable String title)
    {
        return publicationService.findByTitle(title);
    }

    @GetMapping("/find/genre/{genre}")
    public List<Publication> findByGenre(@PathVariable String genre)
    {
        return publicationService.findByGenre(genre);
    }

}
