package wiseSWlife.wiseSWlife.model.graduation.form;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@AllArgsConstructor @Getter @Setter
public class MajorForm {
    private int totalMajorRequirement;//총 전공필수 학점

    private ArrayList<String> majorBeginAndRequirementArr;//기초, 필수 전공명 배열

    private int majorRequirement;//내 전공필수 학점

    private int totalCommonMajor;//총 전공 학점

    private ArrayList<String> majorSelectArr;//선택 전공명 배열

    private int commonMajor;//내 전공학점

    private int futureDesign;//내 미래설계상담
}
