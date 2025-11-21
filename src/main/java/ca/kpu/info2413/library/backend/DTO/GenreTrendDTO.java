package ca.kpu.info2413.library.backend.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GenreTrendDTO {
    private String genre;
    private Long borrowCount;

    public GenreTrendDTO() {}
    public GenreTrendDTO(String genre, Long borrowCount) { this.genre = genre; this.borrowCount = borrowCount; }

}
