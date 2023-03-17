package wiseSWlife.wiseSWlife.dto.graduation.form;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ExamForm {
    private String sid;

    private Boolean bible;

    private Boolean english;

    private Boolean computer;

    private Boolean computer2;
}
