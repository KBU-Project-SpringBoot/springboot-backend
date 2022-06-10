package wiseSWlife.wiseSWlife.domain.graduation.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Data @Setter @Getter
public class CreditForm {
    private int standardCredit;

    private int myCredit;

    private String creditPercentage;
}
