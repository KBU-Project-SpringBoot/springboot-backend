package wiseSWlife.wiseSWlife.service.graduation.standardImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wiseSWlife.wiseSWlife.model.graduation.form.CreditForm;
import wiseSWlife.wiseSWlife.model.graduation.form.GPAForm;
import wiseSWlife.wiseSWlife.model.graduation.form.MajorForm;
import wiseSWlife.wiseSWlife.model.graduation.form.RefinementForm;
import wiseSWlife.wiseSWlife.service.graduation.vo.EnumMapperFactory;
import wiseSWlife.wiseSWlife.service.graduation.vo.EnumMapperValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class StandardImpl implements wiseSWlife.wiseSWlife.service.graduation.standardInterface.Standard {


    @Override
    public CreditForm checkCredit(int myCredit){
        CreditForm creditForm = new CreditForm(myCredit);

        return creditForm;
    }

    @Override
    public GPAForm checkGPA(double myGPA){
        GPAForm gpaForm = new GPAForm(myGPA);

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

        if(myMajorSelect != null){
            for(ArrayList<String> i :myMajorSelect){
                myMajorSelectCnt += Integer.parseInt(i.get(1));
                myMajorSelectArr.add(i.get(0).substring(0, i.get(0).indexOf('(')));
            }
        }

        for(ArrayList<String> i : myMajorRequirement){
            if(i.get(0).contains("미래설계상담")){
                futureDesignCnt++;
            }else{
                myMajorRequirementCnt += Integer.parseInt(i.get(1));
                myMajorBeginAndRequirementArr.add(i.get(0).substring(0, i.get(0).indexOf('(')));
            }
        }

        MajorForm majorForm = new MajorForm(myMajorBeginAndRequirementArr, myMajorBeginCnt + myMajorRequirementCnt, myMajorSelectArr, myMajorBeginCnt + myMajorRequirementCnt + myMajorSelectCnt , futureDesignCnt);

        return majorForm;
    }

    @Override
    public RefinementForm checkRefinement(ArrayList<String>[] myRefinementSelect, ArrayList<String>[] myRefinementRequirement){
        int myRefinementCnt = 0;
        ArrayList<String> myRefinementArr = new ArrayList<>();
        int myEnglishCnt = 0;
        ArrayList<String> myEnglishArr = new ArrayList<>();
        int myBasicClassCnt = 0;
        boolean myCollegeLifeAndSelfDevelopment = false;

        for(ArrayList<String> i : myRefinementSelect){
            myRefinementCnt += Integer.parseInt(i.get(1));
            myRefinementArr.add(i.get(0).substring(0, i.get(0).indexOf('(')));
        }

        for(ArrayList<String> i : myRefinementRequirement){
            if(i.get(0).contains("영어")){
                myEnglishCnt += Integer.parseInt(i.get(1));
                myEnglishArr.add(i.get(0).substring(0, i.get(0).indexOf('(')));
            }else if(i.get(0).contains("학술적글쓰기") || i.get(0).contains("글쓰기의기초") || i.get(0).contains("사고와표현")){
                myBasicClassCnt += Integer.parseInt(i.get(1));
            }else if(i.get(0).contains("대학생활과자기계발")){
                myCollegeLifeAndSelfDevelopment = true;
            }
        }

        RefinementForm refinementForm = new RefinementForm(myRefinementArr, myRefinementCnt, myEnglishArr, myEnglishCnt, myBasicClassCnt, myCollegeLifeAndSelfDevelopment);

        return refinementForm;
    }

}
