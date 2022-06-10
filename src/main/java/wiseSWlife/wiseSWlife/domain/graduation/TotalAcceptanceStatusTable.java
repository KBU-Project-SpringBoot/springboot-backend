package wiseSWlife.wiseSWlife.domain.graduation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Map;

@AllArgsConstructor
@Data @Setter @Getter
public class TotalAcceptanceStatusTable {

    private Map<String, String> summary;

    private ArrayList<String> head;

    private Map<String, ArrayList<String>[]> body;
}
