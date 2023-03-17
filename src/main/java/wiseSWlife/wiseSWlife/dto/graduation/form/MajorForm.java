package wiseSWlife.wiseSWlife.dto.graduation.form;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@AllArgsConstructor @Getter @Setter
@NoArgsConstructor
public class MajorForm {
    private String sid;

    private ArrayList<String> majorBeginAndRequirementArr;//기초, 필수 전공명 배열

    private int majorRequirementCredit;//내 전공필수 학점

    private ArrayList<String> majorSelectArr;//선택 전공명 배열

    private int commonMajor;//내 전공학점

    private int futureDesign;//내 미래설계상담
}
