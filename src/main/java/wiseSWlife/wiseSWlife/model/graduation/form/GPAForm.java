package wiseSWlife.wiseSWlife.model.graduation.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Data @Setter @Getter
public class GPAForm {
    private double standardGPA;

    private double myGPA;
}
