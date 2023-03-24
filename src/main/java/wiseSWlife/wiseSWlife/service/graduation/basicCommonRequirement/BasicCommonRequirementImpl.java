package wiseSWlife.wiseSWlife.service.graduation.basicCommonRequirement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wiseSWlife.wiseSWlife.db.repository.bcrRepository.BCRRepository;
import wiseSWlife.wiseSWlife.dto.graduation.form.BCRForm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import static wiseSWlife.wiseSWlife.service.graduation.totalAcceptanceStatus.TotalAcceptanceStatusImpl.*;

@Service
@RequiredArgsConstructor
public class BasicCommonRequirementImpl implements BasicCommonRequirement {
    public final static int CHAPEL_GRADUATION = 8;//채플
    public final static int CONDUCTION_GRADUATION = 8;//전도훈련
    public final static int BIBLE_STUDY_GRADUATION = 6;//성경 교육
    public final static String CONDUCTION = "전도훈련";
    public final static String CHAPEL = "경건훈련";
    public final static String WHEAT_GRAIN_TRAINING = "밀알훈련";
    public final static String BIBLE = "성서적";
    public final static String GLOBAL_CIVILIZATION = "세계문명";

    private final BCRRepository bcrRepository;

    @Override
    public BCRForm getBCR(String sid, ArrayList<String>[] myBasicCommonRequirement, ArrayList<String>[] myRefinementRequirement){
        HashMap<String, Integer> basicCommonRequirementMap = new HashMap<>();

        checkBCR(myBasicCommonRequirement, myRefinementRequirement, basicCommonRequirementMap);
        boolean wheatGrainTraining = checkWheatGrainTraining(basicCommonRequirementMap);

        BCRForm bcrForm = new BCRForm(sid,
                CHAPEL_GRADUATION - basicCommonRequirementMap.get(CHAPEL),
                CONDUCTION_GRADUATION - basicCommonRequirementMap.get(CONDUCTION),
                wheatGrainTraining,
                BIBLE_STUDY_GRADUATION - basicCommonRequirementMap.get(BIBLE));
        saveBCR(sid, bcrForm);

        return bcrForm;
    }

    @Override
    public void checkBCR(ArrayList<String>[] myBasicCommonRequirement, ArrayList<String>[] myRefinementRequirement, HashMap<String, Integer> basicCommonRequirementMap) {
        for(ArrayList<String> i : myBasicCommonRequirement){
            if(i.get(SUBJECT_NAME_COLUMN).startsWith(CONDUCTION)){
                basicCommonRequirementMap.put(CONDUCTION, basicCommonRequirementMap.getOrDefault(CONDUCTION, 0) + 1);
            }else if(i.get(SUBJECT_NAME_COLUMN).startsWith(CHAPEL)){
                basicCommonRequirementMap.put(CHAPEL, basicCommonRequirementMap.getOrDefault(CHAPEL, 0) + 1);
            }else if(i.get(SUBJECT_NAME_COLUMN).startsWith(WHEAT_GRAIN_TRAINING)){
                basicCommonRequirementMap.put(WHEAT_GRAIN_TRAINING, 0);
            }else if(i.get(SUBJECT_NAME_COLUMN).startsWith(BIBLE) || i.get(0).startsWith(GLOBAL_CIVILIZATION)){
                basicCommonRequirementMap.put(BIBLE, basicCommonRequirementMap.getOrDefault(BIBLE, 0) + 1);
            }
        }

        for(ArrayList<String> i : myRefinementRequirement){
            if(i.get(SUBJECT_NAME_COLUMN).startsWith(CONDUCTION)){
                basicCommonRequirementMap.put(CONDUCTION, basicCommonRequirementMap.getOrDefault(CONDUCTION, 0) + 1);
            }else if(i.get(SUBJECT_NAME_COLUMN).startsWith(CHAPEL)){
                basicCommonRequirementMap.put(CHAPEL, basicCommonRequirementMap.getOrDefault(CHAPEL, 0) + 1);
            }
        }
    }

    private boolean checkWheatGrainTraining(HashMap<String, Integer> basicCommonRequirementMap) {
        return basicCommonRequirementMap.containsKey(WHEAT_GRAIN_TRAINING);
    }

    private void saveBCR(String sid, BCRForm bcrForm) {
        Optional<BCRForm> bcrBySid = bcrRepository.findBCRBySid(sid);
        if (bcrBySid.isEmpty()) {
            bcrRepository.save(bcrForm);
        }
        bcrRepository.update(bcrForm);
    }
}
