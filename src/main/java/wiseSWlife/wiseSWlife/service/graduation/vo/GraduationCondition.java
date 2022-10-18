package wiseSWlife.wiseSWlife.service.graduation.vo;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * sid : 학년과 학번의 조합
 * totalCredit : 졸업학점
 * totalGPA : 졸업기준 평균평점
 * totalMajorRequirement : 전공필수 총학점
 * totalCommonMajor : 기본전공 총학점
 * totalEnglish : 영어 총학점
 * totalBasicClass : 학글 같은 기초수업 총학점
 * totalRefinement : 교양선택 총학점
 */
@Getter
@RequiredArgsConstructor
public enum GraduationCondition implements EnumMapperType{
    컴16( 140,1.5,24,66,12,6,15),
    컴17( 140, 1.5, 24, 66, 12, 6, 15),
    컴18( 140, 1.5, 27, 51, 12, 6, 15),
    컴19( 130, 1.5, 27, 51, 6, 6, 15),
    컴20( 130, 1.5, 27, 51, 6, 6, 15),
    컴21( 130, 1.5, 27, 51, 6, 6, 15),
    컴22( 130, 1.5, 27, 51, 6, 2, 18);

    private final int totalCredit;
    private final double totalGPA;
    private final int totalMajorRequirement;
    private final int totalCommonMajor;
    private final int totalEnglish;
    private final int totalBasicClass;
    private final int totalRefinement;


    @Override
    public String getCode() {
        return name();
    }


}
