package wiseSWlife.wiseSWlife.service.graduation.convertScrapingImpl;

import org.springframework.stereotype.Component;
import wiseSWlife.wiseSWlife.model.graduation.ExamTable;
import wiseSWlife.wiseSWlife.service.graduation.convertScrapingInf.ConvertExamTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class StringToExamTable implements ConvertExamTable {

    @Override
    public Map<String, Boolean> convert(ExamTable table) {
        Map<String, Boolean> examMap = new HashMap<>();
        String[] subjects = {"성경", "영어", "컴퓨터", "컴퓨터2"};

        for(String i : subjects){
            examMap.put(i, false);
        }

        for(ArrayList i:table.getBody()){
            if(i.get(4).equals("합격")){
                examMap.put(i.get(1).toString(),true);
            }
        }
        return examMap;
    }
}
