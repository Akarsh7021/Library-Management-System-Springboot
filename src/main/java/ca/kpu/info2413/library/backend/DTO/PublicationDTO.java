package ca.kpu.info2413.library.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublicationDTO
{
    private Long isbn13;
    private String title;
    private List<String> authors;
    private String genre;
    private Integer pageCount;
    private String ebookUrl;
}