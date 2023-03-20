package wiseSWlife.wiseSWlife.service.graduation.refinement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wiseSWlife.wiseSWlife.dto.graduation.form.RefinementForm;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class RefinementImpl implements Refinement{
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
