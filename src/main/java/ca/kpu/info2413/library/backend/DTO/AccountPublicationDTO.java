package ca.kpu.info2413.library.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountPublicationDTO {
    private Long isbn13;
    private String title;
    private Integer accountId;
    private String accountName;
    private Short waitlistPosition;
}
