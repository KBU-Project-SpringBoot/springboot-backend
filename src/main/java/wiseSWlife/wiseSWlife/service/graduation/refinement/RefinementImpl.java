package wiseSWlife.wiseSWlife.service.graduation.refinement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wiseSWlife.wiseSWlife.db.repository.refinementRepository.RefinementRepository;
import wiseSWlife.wiseSWlife.dto.graduation.form.RefinementForm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import static wiseSWlife.wiseSWlife.service.graduation.totalAcceptanceStatus.TotalAcceptanceStatusImpl.*;

@Service
@RequiredArgsConstructor
public class RefinementImpl implements Refinement{
    private final static String REFINEMENT = "교양";
    private final static String ENGLISH = "영어";
    private final static String ACADEMIC_WRITING = "학술적글쓰기";
    private final static String BASIS_WRITING = "글쓰기의기초";
    private final static String THOUGHT_AND_EXPRESSION = "사고와표현";
    private final static String COLLEGE_LIFE_AND_SELF_DEVELOPMENT = "대학생활과자기계발";

    private final RefinementRepository refinementRepository;

    @Override
    public RefinementForm getRefinementForm(String sid, ArrayList<String>[] myRefinementSelect, ArrayList<String>[] myRefinementRequirement){
        HashMap<String, Integer> refinementMap = new HashMap<>();
        ArrayList<String> myRefinementArr = new ArrayList<>();
        ArrayList<String> myEnglishArr = new ArrayList<>();

        checkRefinementSelect(myRefinementSelect, refinementMap, myRefinementArr);
        checkRefinementRequirement(myRefinementRequirement, refinementMap, myEnglishArr, myRefinementArr);
        boolean myCollegeLifeAndSelfDevelopment = checkCollegeLifeAndSelfDevelopment(refinementMap);

        RefinementForm refinementForm = new RefinementForm(sid, myRefinementArr,
                refinementMap.get(REFINEMENT),
                myEnglishArr, refinementMap.get(ENGLISH),
                refinementMap.get(ACADEMIC_WRITING), myCollegeLifeAndSelfDevelopment);
        saveRefinement(sid, refinementForm);

        return refinementForm;
    }

    @Override
    public void checkRefinementSelect(ArrayList<String>[] myRefinementSelect, HashMap<String, Integer> refinementMap, ArrayList<String> myRefinementArr) {
        for(ArrayList<String> i : myRefinementSelect){
            refinementMap.put(REFINEMENT, refinementMap.getOrDefault(REFINEMENT, 0) + Integer.parseInt(i.get(CREDIT_COLUMN)));
            myRefinementArr.add(i.get(SUBJECT_NAME_COLUMN).substring(0, i.get(SUBJECT_NAME_COLUMN).indexOf(LEFT_BRACKET)));
        }
    }

    @Override
    public void checkRefinementRequirement(ArrayList<String>[] myRefinementRequirement, HashMap<String, Integer> refinementMap, ArrayList<String> myEnglishArr, ArrayList<String> myRefinementArr) {
        for(ArrayList<String> i : myRefinementRequirement){
            if(i.get(SUBJECT_NAME_COLUMN).startsWith(ENGLISH)){
                refinementMap.put(ENGLISH, refinementMap.getOrDefault(ENGLISH, 0) + Integer.parseInt(i.get(CREDIT_COLUMN)));
                myEnglishArr.add(i.get(SUBJECT_NAME_COLUMN).substring(0, i.get(SUBJECT_NAME_COLUMN).indexOf(LEFT_BRACKET)));
            }else if(i.get(SUBJECT_NAME_COLUMN).startsWith(ACADEMIC_WRITING) || i.get(SUBJECT_NAME_COLUMN).startsWith(BASIS_WRITING) || i.get(SUBJECT_NAME_COLUMN).startsWith(THOUGHT_AND_EXPRESSION)){
                refinementMap.put(ACADEMIC_WRITING, refinementMap.getOrDefault(ACADEMIC_WRITING, 0) + Integer.parseInt(i.get(CREDIT_COLUMN)));
            }else if(i.get(SUBJECT_NAME_COLUMN).startsWith(COLLEGE_LIFE_AND_SELF_DEVELOPMENT)){
                refinementMap.put(COLLEGE_LIFE_AND_SELF_DEVELOPMENT, 0);
            }
        }
    }

    private boolean checkCollegeLifeAndSelfDevelopment(HashMap<String, Integer> refinementMap) {
        return refinementMap.containsKey(COLLEGE_LIFE_AND_SELF_DEVELOPMENT);
    }

    private void saveRefinement(String sid, RefinementForm refinementForm) {
        Optional<RefinementForm> refinementBySid = refinementRepository.findRefinementBySid(sid);
        if (refinementBySid.isEmpty()) {
            refinementRepository.save(refinementForm);
        }
        refinementRepository.update(refinementForm);
    }
}
