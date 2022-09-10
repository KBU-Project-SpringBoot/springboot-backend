package wiseSWlife.wiseSWlife.service.graduation.basicCommonRequirement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wiseSWlife.wiseSWlife.model.graduation.form.BCRForm;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ParsingBCR {
    private int chapel = 8;//채플
    private int conduction = 8;//전도훈련
    private Boolean wheatGrain = false;//밀알훈련
    private int bibleStudy = 6;//성경 교육

    public BCRForm getStudy(ArrayList<String>[] arr){

        for(ArrayList<String> i : arr){
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

        BCRForm bcrForm = new BCRForm(chapel, conduction, wheatGrain, bibleStudy);

        return bcrForm;
    }
}
