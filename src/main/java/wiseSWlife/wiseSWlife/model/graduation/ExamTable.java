package wiseSWlife.wiseSWlife.model.graduation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@AllArgsConstructor
@Data @Setter @Getter
public class ExamTable {

    private ArrayList<String> head;

    private ArrayList<String>[] body;

}
