package ca.kpu.info2413.library.backend;

public class PublicationPublicationId implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Column(name = "isbn_13_publication")
    private Integer isbn13Publication;

    @Column(name = "isbn_13_publication1")
    private Integer isbn13Publication1;

}
