package ca.kpu.info2413.library.backend.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PublicationReportDTO {
    private Integer isbn13;
    private String title;
    private String genre;
    private Long borrowCount;

    public PublicationReportDTO() {}
    public PublicationReportDTO(Integer isbn13, String title, String genre, Long borrowCount) {
        this.isbn13 = isbn13; this.title = title; this.genre = genre; this.borrowCount = borrowCount;
    }

}
