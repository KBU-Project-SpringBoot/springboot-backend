package wiseSWlife.wiseSWlife.service.graduation.major;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wiseSWlife.wiseSWlife.db.repository.majorRepository.MajorRepository;
import wiseSWlife.wiseSWlife.dto.graduation.form.MajorForm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import static wiseSWlife.wiseSWlife.service.graduation.totalAcceptanceStatus.TotalAcceptanceStatusImpl.*;

@Service
@RequiredArgsConstructor
public class MajorImpl implements Major {
    private final static String MAJOR_BEGIN = "전공기초";
    private final static String MAJOR_REQUIREMENT = "전공필수";
    private final static String MAJOR_SELECT = "전공선택";
    private final static String FUTURE_AND_DESIGN = "미래설계상담";

    private final MajorRepository majorRepository;

    @Override
    public MajorForm getMajorForm(String sid, ArrayList<String>[] intranetMyMajorBegin, ArrayList<String>[] intranetMyMajorSelect, ArrayList<String>[] intranetMyMajorRequirement) {
        HashMap<String, Integer> majorMap = new HashMap<>();
        ArrayList<String> myMajorSelectArr = new ArrayList<>();
        ArrayList<String> myMajorBeginAndRequirementArr = new ArrayList<>();

        checkMajorBegin(intranetMyMajorBegin, majorMap, myMajorBeginAndRequirementArr);
        checkMajorRequirement(intranetMyMajorRequirement, majorMap, myMajorBeginAndRequirementArr);
        checkMajorSelect(intranetMyMajorSelect, majorMap, myMajorSelectArr);
        checkFutureDesign(intranetMyMajorRequirement, majorMap);

        MajorForm majorForm = new MajorForm(sid, myMajorBeginAndRequirementArr,
                majorMap.get(MAJOR_BEGIN) + majorMap.get(MAJOR_REQUIREMENT),
                myMajorSelectArr,
                majorMap.get(MAJOR_BEGIN) + majorMap.get(MAJOR_REQUIREMENT) + majorMap.get(MAJOR_SELECT),
                majorMap.get(FUTURE_AND_DESIGN));

        saveMajor(sid, majorForm);

        return majorForm;
    }

    @Override
    public void checkMajorBegin(ArrayList<String>[] intranetMyBeginMajor, HashMap<String, Integer> majorMap, ArrayList<String> myMajorBeginAndRequirementArr) {
        for (ArrayList<String> i : intranetMyBeginMajor) {
            majorMap.put(MAJOR_BEGIN, majorMap.getOrDefault(MAJOR_BEGIN, 0) + Integer.parseInt(i.get(CREDIT_COLUMN)));
            myMajorBeginAndRequirementArr.add(i.get(SUBJECT_NAME_COLUMN).substring(0, i.get(SUBJECT_NAME_COLUMN).indexOf(LEFT_BRACKET)));
        }
    }

    @Override
    public void checkMajorRequirement(ArrayList<String>[] intranetMyRequirementMajor, HashMap<String, Integer> majorMap, ArrayList<String> myMajorBeginAndRequirementArr) {
        for (ArrayList<String> i : intranetMyRequirementMajor) {
            if (i.get(SUBJECT_NAME_COLUMN).contains(FUTURE_AND_DESIGN)) {
                continue;
            }
            majorMap.put(MAJOR_REQUIREMENT, majorMap.getOrDefault(MAJOR_REQUIREMENT, 0) + Integer.parseInt(i.get(CREDIT_COLUMN)));
            myMajorBeginAndRequirementArr.add(i.get(SUBJECT_NAME_COLUMN).substring(0, i.get(SUBJECT_NAME_COLUMN).indexOf(LEFT_BRACKET)));
        }
    }

    @Override
    public void checkMajorSelect(ArrayList<String>[] myMajorSelect, HashMap<String, Integer> majorMap, ArrayList<String> myMajorSelectArr) {
        if (myMajorSelect != null) {
            for (ArrayList<String> i : myMajorSelect) {
                majorMap.put(MAJOR_SELECT, majorMap.getOrDefault(MAJOR_SELECT, 0) + Integer.parseInt(i.get(CREDIT_COLUMN)));
                myMajorSelectArr.add(i.get(SUBJECT_NAME_COLUMN).substring(0, i.get(SUBJECT_NAME_COLUMN).indexOf(LEFT_BRACKET)));
            }
        }
    }

    @Override
    public void checkFutureDesign(ArrayList<String>[] intranetMyRequirementMajor, HashMap<String, Integer> majorMap) {
        for (ArrayList<String> i : intranetMyRequirementMajor) {
            if (i.get(SUBJECT_NAME_COLUMN).contains(FUTURE_AND_DESIGN)) {
                majorMap.put(FUTURE_AND_DESIGN, majorMap.getOrDefault(FUTURE_AND_DESIGN, 0) + 1);
            }
        }
    }

    private void saveMajor(String sid, MajorForm majorForm) {
        Optional<MajorForm> majorBySid = majorRepository.findMajorBySid(sid);
        if (majorBySid.isEmpty()) {
            majorRepository.save(majorForm);
        }
        majorRepository.update(majorForm);
    }
}
