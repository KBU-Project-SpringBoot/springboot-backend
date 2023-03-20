package wiseSWlife.wiseSWlife.service.graduation.major;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wiseSWlife.wiseSWlife.db.repository.majorRepository.MajorRepository;
import wiseSWlife.wiseSWlife.dto.graduation.form.MajorForm;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MajorImpl implements Major{
    private final MajorRepository majorRepository;

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

        saveMajor(sid, majorForm);

        return majorForm;
    }

    private void saveMajor(String sid, MajorForm majorForm) {
        Optional<MajorForm> majorBySid = majorRepository.findMajorBySid(sid);
        if(majorBySid.isEmpty()){
            majorRepository.save(majorForm);
        }
        majorRepository.update(majorForm);
    }
}
