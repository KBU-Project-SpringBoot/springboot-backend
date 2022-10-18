package wiseSWlife.wiseSWlife.service.graduation.basicCommonRequirement;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import wiseSWlife.wiseSWlife.model.graduation.form.BCRForm;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@PropertySource("graduation.properties")
public class ParsingBCR {

    public BCRForm getStudy(ArrayList<String>[] myBasicCommonRequirement, ArrayList<String>[] myRefinementRequirement){
        int chapel = 8;//채플
        int conduction = 8;//전도훈련
        Boolean wheatGrain = false;//밀알훈련
        int bibleStudy = 6;//성경 교육

        for(ArrayList<String> i : myBasicCommonRequirement){
            if(i.get(0).contains("전도훈련")){
                conduction--;
            }else if(i.get(0).contains("경건훈련")){
                chapel--;
            }else if(i.get(0).contains("밀알훈련")){
                wheatGrain = true;
            }else if(i.get(0).contains("성서적") || i.get(0).contains("세계문명")){
                bibleStudy--;
            }
        }

        for(ArrayList<String> i : myRefinementRequirement){
            if(i.get(0).contains("전도훈련")){
                conduction--;
            }else if(i.get(0).contains("경건훈련")){
                chapel--;
            }
        }

        BCRForm bcrForm = new BCRForm(chapel, conduction, wheatGrain, bibleStudy);

        return bcrForm;
    }
}
