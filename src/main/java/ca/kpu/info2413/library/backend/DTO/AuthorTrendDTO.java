package ca.kpu.info2413.library.backend.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthorTrendDTO {
    private String authorName;
    private Long borrowCount;

    public AuthorTrendDTO() {}
    public AuthorTrendDTO(String authorName, Long borrowCount) { this.authorName = authorName; this.borrowCount = borrowCount; }

}
