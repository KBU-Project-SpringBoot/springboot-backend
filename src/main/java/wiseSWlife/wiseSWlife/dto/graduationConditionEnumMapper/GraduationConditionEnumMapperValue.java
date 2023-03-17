package wiseSWlife.wiseSWlife.dto.graduationConditionEnumMapper;

import lombok.Getter;

@Getter
public class GraduationConditionEnumMapperValue {

    private String code;
    private int totalCredit;
    private double totalGPA;
    private int totalMajorRequirement;
    private int totalCommonMajor;
    private int totalEnglish;
    private int totalBasicClass;
    private int totalRefinement;

    public GraduationConditionEnumMapperValue(GraduationConditionEnumMapperType graduationConditionEnumMapperType){
        code = graduationConditionEnumMapperType.getCode();
        totalCredit = graduationConditionEnumMapperType.getTotalCredit();
        totalGPA = graduationConditionEnumMapperType.getTotalGPA();
        totalMajorRequirement = graduationConditionEnumMapperType.getTotalMajorRequirement();
        totalCommonMajor = graduationConditionEnumMapperType.getTotalCommonMajor();
        totalEnglish = graduationConditionEnumMapperType.getTotalEnglish();
        totalBasicClass = graduationConditionEnumMapperType.getTotalBasicClass();
        totalRefinement = graduationConditionEnumMapperType.getTotalRefinement();
    }
}
