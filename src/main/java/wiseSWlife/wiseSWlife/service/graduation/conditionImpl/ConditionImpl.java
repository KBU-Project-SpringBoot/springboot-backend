package wiseSWlife.wiseSWlife.service.graduation.conditionImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wiseSWlife.wiseSWlife.dto.graduation.form.CreditForm;
import wiseSWlife.wiseSWlife.dto.graduation.form.GPAForm;
import wiseSWlife.wiseSWlife.dto.graduation.form.MajorForm;
import wiseSWlife.wiseSWlife.dto.graduation.form.RefinementForm;
import wiseSWlife.wiseSWlife.service.graduation.conditionInf.Condition;

import java.util.ArrayList;



@Service
@RequiredArgsConstructor
public class ConditionImpl implements Condition {


    @Override
    public CreditForm checkCredit(String sid, int myCredit){
        CreditForm creditForm = new CreditForm(sid, myCredit);

        return creditForm;
    }

    @Override
    public GPAForm checkGPA(String sid, double myGPA){
        GPAForm gpaForm = new GPAForm(sid, myGPA);

        return gpaForm;
    }

    @Override
    public MajorForm checkMajor(String sid, ArrayList<String>[] myMajorBegin, ArrayList<String>[] myMajorSelect, ArrayList<String>[] myMajorRequirement){
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

        MajorForm majorForm = new MajorForm(sid, myMajorBeginAndRequirementArr, myMajorBeginCnt + myMajorRequirementCnt, myMajorSelectArr, myMajorBeginCnt + myMajorRequirementCnt + myMajorSelectCnt , futureDesignCnt);

        return majorForm;
    }

    @Override
    public RefinementForm checkRefinement(String sid, ArrayList<String>[] myRefinementSelect, ArrayList<String>[] myRefinementRequirement){
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

        RefinementForm refinementForm = new RefinementForm(sid, myRefinementArr, myRefinementCnt, myEnglishArr, myEnglishCnt, myBasicClassCnt, myCollegeLifeAndSelfDevelopment);

        return refinementForm;
    }

}
