package wiseSWlife.wiseSWlife.service.graduation.major;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wiseSWlife.wiseSWlife.db.repository.majorRepository.MajorRepository;
import wiseSWlife.wiseSWlife.dto.graduation.form.MajorForm;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MajorImpl implements Major {
    private final MajorRepository majorRepository;

    @Override
    public MajorForm getMajorForm(String sid, ArrayList<String>[] intranetMyMajorBegin, ArrayList<String>[] intranetMyMajorSelect, ArrayList<String>[] intranetMyMajorRequirement) {
        ArrayList<String> myMajorSelectArr = new ArrayList<>();
        ArrayList<String> myMajorBeginAndRequirementArr = new ArrayList<>();

        int majorBeginCreditResult = checkMajorBegin(intranetMyMajorBegin, myMajorBeginAndRequirementArr);
        int majorRequirementCreditResult = checkMajorRequirement(intranetMyMajorRequirement, myMajorBeginAndRequirementArr);
        int futureDesignCntResult = checkFutureDesign(intranetMyMajorRequirement);
        int majorSelectCreditResult = checkMajorSelect(intranetMyMajorSelect, myMajorSelectArr);

        MajorForm majorForm = new MajorForm(sid, myMajorBeginAndRequirementArr, majorBeginCreditResult + majorRequirementCreditResult, myMajorSelectArr, majorBeginCreditResult + majorRequirementCreditResult + majorSelectCreditResult, futureDesignCntResult);
        saveMajor(sid, majorForm);

        return majorForm;
    }

    @Override
    public int checkMajorBegin(ArrayList<String>[] intranetMyBeginMajor, ArrayList<String> myMajorBeginAndRequirementArr) {
        int majorBeginCreditResult = 0;
        for (ArrayList<String> i : intranetMyBeginMajor) {
            majorBeginCreditResult += Integer.parseInt(i.get(1));
            myMajorBeginAndRequirementArr.add(i.get(0).substring(0, i.get(0).indexOf('(')));
        }

        return majorBeginCreditResult;
    }

    @Override
    public int checkMajorRequirement(ArrayList<String>[] intranetMyRequirementMajor, ArrayList<String> myMajorBeginAndRequirementArr) {
        int majorSelectCreditResult = 0;
        for (ArrayList<String> i : intranetMyRequirementMajor) {
            if (i.get(0).contains("미래설계상담")) {
                continue;
            }

            majorSelectCreditResult += Integer.parseInt(i.get(1));
            myMajorBeginAndRequirementArr.add(i.get(0).substring(0, i.get(0).indexOf('(')));
        }

        return majorSelectCreditResult;
    }

    @Override
    public int checkFutureDesign(ArrayList<String>[] intranetMyRequirementMajor) {
        int futureDesignCntResult = 0;
        for (ArrayList<String> i : intranetMyRequirementMajor) {
            if (i.get(0).contains("미래설계상담")) {
                futureDesignCntResult++;
            }
        }

        return futureDesignCntResult;
    }

    @Override
    public int checkMajorSelect(ArrayList<String>[] myMajorSelect, ArrayList<String> myMajorSelectArr) {
        int majorRequirementCreditResult = 0;
        if (myMajorSelect != null) {
            for (ArrayList<String> i : myMajorSelect) {
                majorRequirementCreditResult += Integer.parseInt(i.get(1));
                myMajorSelectArr.add(i.get(0).substring(0, i.get(0).indexOf('(')));
            }
        }

        return majorRequirementCreditResult;
    }

    private void saveMajor(String sid, MajorForm majorForm) {
        Optional<MajorForm> majorBySid = majorRepository.findMajorBySid(sid);
        if (majorBySid.isEmpty()) {
            majorRepository.save(majorForm);
        }
        majorRepository.update(majorForm);
    }
}
