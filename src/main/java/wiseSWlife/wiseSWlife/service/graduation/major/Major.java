package wiseSWlife.wiseSWlife.service.graduation.major;

import wiseSWlife.wiseSWlife.dto.graduation.form.MajorForm;

import java.util.ArrayList;
import java.util.HashMap;

public interface Major {
    MajorForm getMajorForm(String sid, ArrayList<String>[] intranetMyMajorBegin, ArrayList<String>[] intranetMyMajorSelect, ArrayList<String>[] intranetMyMajorRequirement);

    void checkMajorBegin(ArrayList<String>[] intranetMyBeginMajor, HashMap<String, Integer> majorMap, ArrayList<String> myMajorBeginAndRequirementArr);

    void checkMajorRequirement(ArrayList<String>[] intranetMyRequirementMajor, HashMap<String, Integer> majorMap, ArrayList<String> myMajorBeginAndRequirementArr);

    void checkMajorSelect(ArrayList<String>[] myMajorSelect, HashMap<String, Integer> majorMap, ArrayList<String> myMajorSelectArr);

    void checkFutureDesign(ArrayList<String>[] intranetMyRequirementMajor, HashMap<String, Integer> majorMap);
}
