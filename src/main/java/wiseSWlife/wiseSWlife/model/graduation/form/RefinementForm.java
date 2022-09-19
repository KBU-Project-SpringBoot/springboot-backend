package wiseSWlife.wiseSWlife.model.graduation.form;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@AllArgsConstructor @Getter @Setter
public class RefinementForm {
    private int totalRefinement;//총 교양 학점

    private ArrayList<String> refinementArr;//교양명 배열

    private int refinement;//내 교양 학점

    private int totalEnglish;//총 영어 학점

    private ArrayList<String> EnglishArr;//영어수업명 배열

    private int english;//내 영어 학점

    private int totalBasicClass;//총 기초교양수업 학점

    private int basicClass;//내 기초교양수업 학점

    private boolean collegeLifeAndSelfDevelopment;//대학생활과 자기개발
}
