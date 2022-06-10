package wiseSWlife.wiseSWlife.domain.graduation.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Data @Setter @Getter
public class GPAForm {
    private double standardGPA;

    private double myGPA;

    private String passGPA;
}
