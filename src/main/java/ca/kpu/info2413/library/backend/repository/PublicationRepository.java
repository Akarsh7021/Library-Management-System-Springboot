package ca.kpu.info2413.library.backend.repository;

import ca.kpu.info2413.library.backend.model.Publication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PublicationRepository extends JpaRepository<Publication, Integer>
{

    // Title search — loads publicationAuthors + authors to avoid lazy proxy at serialization time
    @Query("""
            SELECT DISTINCT p FROM Publication p
            LEFT JOIN FETCH p.publicationAuthors pa
            LEFT JOIN FETCH pa.author a
            WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :title, '%'))
            """)
    List<Publication> findByTitleContainingIgnoreCaseFetchAuthors(@Param("title") String title);

    // Genre search (contains, case-insensitive)
    @Query("""
            SELECT DISTINCT p FROM Publication p
            LEFT JOIN FETCH p.publicationAuthors pa
            LEFT JOIN FETCH pa.author a
            WHERE LOWER(p.genre) LIKE LOWER(CONCAT('%', :genre, '%'))
            """)
    List<Publication> findByGenreIgnoreCaseFetchAuthors(@Param("genre") String genre);

    // Author search: find publications by author name (case-insensitive)
    @Query("""
            SELECT DISTINCT p FROM Publication p
            JOIN p.publicationAuthors pa
            JOIN pa.author a
            LEFT JOIN FETCH p.publicationAuthors pa2
            LEFT JOIN FETCH pa2.author a2
            WHERE LOWER(a.authorName) LIKE LOWER(CONCAT('%', :authorName, '%'))
            """)
    List<Publication> findByAuthorNameLikeIgnoreCaseFetch(@Param("authorName") String authorName);

    // Optional helper: get all with authors (used by findAll if needed)
    @Query("""
            SELECT DISTINCT p FROM Publication p
            LEFT JOIN FETCH p.publicationAuthors pa
            LEFT JOIN FETCH pa.author a
            """)
    List<Publication> findAllFetchAuthors();

    // get all available genres from Publication
    @Query("""
            SELECT DISTINCT p.genre FROM Publication p
            """)
    List<String> getGenres();

    // get all books from a genre except a specified book
    @Query("""
            SELECT DISTINCT p FROM Publication p
            LEFT JOIN FETCH p.publicationAuthors pa
            LEFT JOIN FETCH pa.author a
            WHERE LOWER(p.genre) LIKE LOWER(CONCAT('%', :genre, '%'))
                        AND p.isbn13 <> :isbn_13
            """)
    List<Publication> recBook(@Param("genre") String genre,  @Param("isbn_13") Integer isbn_13);
}