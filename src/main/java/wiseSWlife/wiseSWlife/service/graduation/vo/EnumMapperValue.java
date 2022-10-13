package wiseSWlife.wiseSWlife.service.graduation.vo;

import lombok.Getter;

@Getter
public class EnumMapperValue {

    private String code;
    private int totalCredit;
    private double totalGPA;
    private int totalMajorRequirement;
    private int totalCommonMajor;
    private int totalEnglish;
    private int totalBasicClass;
    private int totalRefinement;

    public EnumMapperValue(EnumMapperType enumMapperType){
        code = enumMapperType.getCode();
        totalCredit = enumMapperType.getTotalCredit();
        totalGPA = enumMapperType.getTotalGPA();
        totalMajorRequirement = enumMapperType.getTotalMajorRequirement();
        totalCommonMajor = enumMapperType.getTotalCommonMajor();
        totalEnglish = enumMapperType.getTotalEnglish();
        totalBasicClass = enumMapperType.getTotalBasicClass();
        totalRefinement = enumMapperType.getTotalRefinement();
    }
}
