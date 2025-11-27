package ca.kpu.info2413.library.backend.controller;

import ca.kpu.info2413.library.backend.DTO.PublicationCreateDTO;
import ca.kpu.info2413.library.backend.DTO.PublicationDTO;
import ca.kpu.info2413.library.backend.model.Publication;
import ca.kpu.info2413.library.backend.service.PublicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publication")
@CrossOrigin(origins = "*")
public class PublicationController
{

    @Autowired
    private PublicationService publicationService;

    @GetMapping
    public List<PublicationDTO> findAll()
    {
        return publicationService.findAll();
    }

    @GetMapping("/{isbn13}")
    public List<PublicationDTO> findByIsbn13(@PathVariable Integer isbn13)
    {
        return publicationService.findByIsbn13(isbn13);
    }

    @PostMapping
    public Publication create(@RequestBody PublicationCreateDTO dto)
    {
        System.out.println("DTO authors: " + dto.getAuthors());
        Publication publication = new Publication();
        publication.setIsbn13(dto.getIsbn13());
        publication.setTitle(dto.getTitle());
        publication.setPublicationDate(dto.getPublicationDate());
        publication.setPageCount(dto.getPageCount());
        publication.setGenre(dto.getGenre());
        publication.setAuthors(dto.getAuthors());

        System.out.println("Creating publication: " + publication.getTitle());
        System.out.println("Authors in request: " + (publication.getInputAuthors() != null ? publication.getInputAuthors().size() : 0));
        return publicationService.save(publication);
    }

    @PutMapping
    public Publication update(@RequestBody Publication publication)
    {
        return publicationService.save(publication);
    }

    @DeleteMapping("/{isbn13}")
    public void deleteById(@PathVariable Integer isbn13)
    {
        publicationService.deleteByIsbn13(isbn13);
    }

    // --- Catalog button search ---
    @GetMapping("/search")
    public List<PublicationDTO> searchPublications(
            @RequestParam(defaultValue = "book") String type,
            @RequestParam String query
    )
    {
        return publicationService.search(type, query);
    }

    // --- Homepage combined search ---
    @GetMapping("/searchHomepage")
    public List<PublicationDTO> searchHomepage(@RequestParam String query)
    {
        return publicationService.searchHomepage(query);
    }
}