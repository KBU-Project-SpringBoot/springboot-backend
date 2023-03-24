package wiseSWlife.wiseSWlife.service.graduation.refinement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wiseSWlife.wiseSWlife.db.repository.refinementRepository.RefinementRepository;
import wiseSWlife.wiseSWlife.dto.graduation.form.RefinementForm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RefinementImpl implements Refinement{
    private final static String REFINEMENT_SELECT = "교양선택";
    private final static String REFINEMENT_REQUIREMENT = "교양필수";
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
        checkRefinementRequirement(myRefinementRequirement, refinementMap, myEnglishArr);
        boolean myCollegeLifeAndSelfDevelopment = checkCollegeLifeAndSelfDevelopment(refinementMap);

        RefinementForm refinementForm = new RefinementForm(sid, myRefinementArr, refinementMap.get(REFINEMENT_REQUIREMENT), myEnglishArr, refinementMap.get(ENGLISH), refinementMap.get(REFINEMENT_SELECT), myCollegeLifeAndSelfDevelopment);
        saveRefinement(sid, refinementForm);

        return refinementForm;
    }

    @Override
    public void checkRefinementSelect(ArrayList<String>[] myRefinementSelect, HashMap<String, Integer> refinementMap, ArrayList<String> myRefinementArr) {
        for(ArrayList<String> i : myRefinementSelect){
            refinementMap.put(REFINEMENT_SELECT, refinementMap.getOrDefault(REFINEMENT_SELECT, 0) + Integer.parseInt(i.get(1)));
            myRefinementArr.add(i.get(0).substring(0, i.get(0).indexOf('(')));
        }
    }

    @Override
    public void checkRefinementRequirement(ArrayList<String>[] myRefinementRequirement, HashMap<String, Integer> refinementMap, ArrayList<String> myEnglishArr) {
        for(ArrayList<String> i : myRefinementRequirement){
            if(i.get(0).contains(ENGLISH)){
                refinementMap.put(ENGLISH, refinementMap.getOrDefault(ENGLISH, 0) + Integer.parseInt(i.get(1)));
                myEnglishArr.add(i.get(0).substring(0, i.get(0).indexOf('(')));
            }else if(i.get(0).contains(ACADEMIC_WRITING) || i.get(0).contains(BASIS_WRITING) || i.get(0).contains(THOUGHT_AND_EXPRESSION)){
                refinementMap.put(REFINEMENT_REQUIREMENT, refinementMap.getOrDefault(REFINEMENT_REQUIREMENT, 0) + Integer.parseInt(i.get(1)));
            }else if(i.get(0).contains(COLLEGE_LIFE_AND_SELF_DEVELOPMENT)){
                refinementMap.put(COLLEGE_LIFE_AND_SELF_DEVELOPMENT, 0);
            }
        }
    }

    private boolean checkCollegeLifeAndSelfDevelopment(HashMap<String, Integer> refinementMap) {
        boolean myCollegeLifeAndSelfDevelopment = false;
        if (refinementMap.containsKey(COLLEGE_LIFE_AND_SELF_DEVELOPMENT)) {
            myCollegeLifeAndSelfDevelopment = true;
        }
        return myCollegeLifeAndSelfDevelopment;
    }

    private void saveRefinement(String sid, RefinementForm refinementForm) {
        Optional<RefinementForm> refinementBySid = refinementRepository.findRefinementBySid(sid);
        if (refinementBySid.isEmpty()) {
            refinementRepository.save(refinementForm);
        }
        refinementRepository.update(refinementForm);
    }
}
