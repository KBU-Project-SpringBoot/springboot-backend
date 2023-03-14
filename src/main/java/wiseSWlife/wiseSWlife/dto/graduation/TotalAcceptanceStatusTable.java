package wiseSWlife.wiseSWlife.dto.graduation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Map;

/**
 * 전체이수현황 테이블 모델
 */
@RequiredArgsConstructor
@Setter @Getter
public class TotalAcceptanceStatusTable {

    @NotNull
    private Map<String, String> summary;

    @NotNull
    private ArrayList<String> head;

    @NotNull
    private Map<String, ArrayList<String>[]> body;
}
