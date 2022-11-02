package wiseSWlife.wiseSWlife.model.vo;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import wiseSWlife.wiseSWlife.model.graduationConditionEnumMapper.GraduationConditionEnumMapperType;

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
public enum GraduationConditionEnum implements GraduationConditionEnumMapperType {
    성16(140, 1.5, 20, 66, 12, 6, 15),
    성17(140, 1.5, 20, 66, 12, 6, 15),
    성18(140, 1.5, 27, 66, 12, 6, 15),
    성19(130, 1.5, 30, 66, 6, 6, 15),
    성20(130, 1.5, 24, 66, 6, 6, 15),
    성21(130, 1.5, 27, 66, 6, 6, 15),
    성22(130, 1.5, 23, 53, 6, 2, 18),

    사16(140, 1.5, 30,66,12,6,15),
    사17(140, 1.5, 30, 66, 12, 6, 15),
    사18(140, 1.5, 27, 51, 12, 6, 15),
    사19(130, 1.5, 27, 51, 6, 6, 15),
    사20(130, 1.5, 27, 51, 6, 6, 15),
    사21(130, 1.5, 27, 51, 6, 6, 15),
    사22(130, 1.5, 27, 51, 6, 2, 18),

    영16(140, 1.5, 24,66,12,6,15),
    영17(140, 1.5, 24, 66, 12, 6, 15),
    영18(140, 1.5, 27, 51, 12, 6, 15),
    영19(130, 1.5, 24, 51, 6, 6, 15),
    영20(130, 1.5, 24, 51, 6, 6, 15),
    영21(130, 1.5, 24, 51, 6, 6, 15),
    영22(130, 1.5, 24, 51, 6, 2, 18),

    컴16(140, 1.5, 24, 66, 12, 6,15),
    컴17(140, 1.5, 24, 66, 12, 6, 15),
    컴18(140, 1.5, 27, 51, 12, 6, 15),
    컴19(130, 1.5, 27, 51, 6, 6, 15),
    컴20(130, 1.5, 27, 51, 6, 6, 15),
    컴21(130, 1.5, 27, 51, 6, 6, 15),
    컴22(130, 1.5, 27, 51, 6, 2, 18),

    간16(146, 1.5, 88, 88, 12, 6,15),
    간17(146, 1.5, 88, 88, 12, 6, 15),
    간18(140, 1.5, 72, 94, 12, 6, 15),
    간19(130, 1.5, 70, 93, 6, 6, 15),
    간20(130, 1.5, 70, 93, 6, 6, 15),
    간21(139, 1.5, 90, 99, 6, 6, 15),
    간22(138, 1.5, 93, 101, 6, 2, 18);

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
