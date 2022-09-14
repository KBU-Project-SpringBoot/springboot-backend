package wiseSWlife.wiseSWlife.service.graduation.standardImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import wiseSWlife.wiseSWlife.model.graduation.form.CreditForm;
import wiseSWlife.wiseSWlife.model.graduation.form.GPAForm;
import wiseSWlife.wiseSWlife.model.graduation.form.MajorForm;
import wiseSWlife.wiseSWlife.service.graduation.standardInterface.Standard;

import java.util.ArrayList;


@Service
@RequiredArgsConstructor
@PropertySource("graduation.properties")
public class Standard2017 implements Standard {

    @Value("${totalCredit.2017}")
    String totalCredit;//졸업학점

    @Value("${totalGPA.2017}")
    String totalGPA;//졸업기준 평균평점

    @Value("${totalMajorRequirement.2017}")
    String totalMajorRequirement;//전공필수 총학점

    @Value("${totalCommonMajor.2017}")
    String totalCommonMajor;//기본전공 총학점

    @Override
    public CreditForm percentageGraduationCredit(int myCredit){
        double creditPercentage = (double) myCredit / Integer.parseInt(totalCredit) * 100.0;
        String formatingPercent = String.format("%.2f",creditPercentage);

        CreditForm creditForm = new CreditForm(Integer.parseInt(totalCredit),myCredit,formatingPercent);

        return creditForm;
    }

    @Override
    public GPAForm percentageGradationGPA(double myGPA){
        String resultGPA = (myGPA > Double.parseDouble(totalGPA)) ? "공부를 열심히 하셨네요!" : "공부를 얼마나 안한거야...?";

        GPAForm gpaForm = new GPAForm(Double.parseDouble(totalGPA), myGPA,resultGPA);

        return gpaForm;
    }

    @Override
    public MajorForm checkMajor(ArrayList<String>[] myMajorBegin, ArrayList<String>[] myMajorSelect, ArrayList<String>[] myMajorRequirement){
        int myMajorBeginCnt = 0;
        int myMajorSelectCnt = 0;
        int myMajorRequirementCnt = 0;
        int futureDesignCnt = 0;
        ArrayList<String> myMajorBeginAndRequirementArr = new ArrayList<>();
        ArrayList<String> myMajorSelectArr = new ArrayList<>();

        for(ArrayList<String> i :myMajorBegin){
            myMajorBeginCnt += Integer.parseInt(i.get(1));
            myMajorBeginAndRequirementArr.add(i.get(0).substring(0, i.get(0).indexOf('(')));
        }

        for(ArrayList<String> i :myMajorSelect){
            myMajorSelectCnt += Integer.parseInt(i.get(1));
            myMajorSelectArr.add(i.get(0).substring(0, i.get(0).indexOf('(')));
        }

        for(ArrayList<String> i : myMajorRequirement){
            if(i.get(0).contains("미래설계상담")){
                futureDesignCnt++;
            }else{
                myMajorRequirementCnt += Integer.parseInt(i.get(1));
                myMajorBeginAndRequirementArr.add(i.get(0).substring(0, i.get(0).indexOf('(')));
            }
        }

        MajorForm majorForm = new MajorForm(Integer.parseInt(totalMajorRequirement), myMajorBeginAndRequirementArr, myMajorBeginCnt + myMajorRequirementCnt, Integer.parseInt(totalCommonMajor), myMajorSelectArr, myMajorBeginCnt + myMajorRequirementCnt + myMajorSelectCnt , futureDesignCnt);

        return majorForm;
    }
}
