package ca.kpu.info2413.library.backend.controller;

import ca.kpu.info2413.library.backend.model.Account;
import ca.kpu.info2413.library.backend.model.Author;
import ca.kpu.info2413.library.backend.service.AccountService;
import ca.kpu.info2413.library.backend.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorController
{

    @Autowired
    private AuthorService authorService;

    @GetMapping
    public List<Author> findAll()
    {
        return authorService.findAll();
    }

    @GetMapping("/{author_id}")
    public List<Author> findByAuthorId(@PathVariable Integer author_id)
    {
        return authorService.findByAuthorId(author_id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Author create(@RequestBody Author author)
    {
        return authorService.save(author);
    }

    @PutMapping
    public Author update(@RequestBody Author author)
    {
        return authorService.save(author);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{author_id}")
    public void deleteByAuthorId(@PathVariable Integer author_id)
    {
        authorService.deleteByAuthorId(author_id);
    }

    /// ///

    @GetMapping("/find/author_name/{author_name}")
    public List<Author> findByAuthorName(@PathVariable String author_name)
    {
        return authorService.findByAuthorName(author_name);
    }

}
