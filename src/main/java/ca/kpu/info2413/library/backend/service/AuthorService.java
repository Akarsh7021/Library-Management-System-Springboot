package ca.kpu.info2413.library.backend.service;

import ca.kpu.info2413.library.backend.model.Author;
import ca.kpu.info2413.library.backend.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService
{
    @Autowired
    AuthorRepository authorRepository;


    public List<Author> findAll()
    {
        return authorRepository.findAll();
    }

    public List<Author> findByAuthorId(Integer authorId)
    {
        return authorRepository.findByAuthorId(authorId);
    }

    public Author save(Author author)
    {
        return authorRepository.save(author);
    }

    public void deleteByAuthorId(Integer authorId)
    {
        authorRepository.deleteById(authorId);
    }

    /// ////////////////////


    public List<Author> findByAuthorName(String authorName)
    {
        return authorRepository.findByAuthorName(authorName);
    }

}

