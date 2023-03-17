package wiseSWlife.wiseSWlife.dto.graduation.form;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@AllArgsConstructor @Getter @Setter
@NoArgsConstructor
public class RefinementForm {
    private String sid;

    private ArrayList<String> refinementArr;//교양명 배열

    private int refinementCredit;//내 교양 학점

    private ArrayList<String> EnglishArr;//영어수업명 배열

    private int englishCredit;//내 영어 학점

    private int basicClassCredit;//내 기초교양수업 학점

    private boolean collegeLifeAndSelfDevelopment;//대학생활과 자기개발
}
