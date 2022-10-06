package wiseSWlife.wiseSWlife.model.graduation;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@RequiredArgsConstructor @Setter @Getter
public class ExamTable {

    @NotNull
    private ArrayList<String> head;

    @NotNull
    private ArrayList<String>[] body;

}
