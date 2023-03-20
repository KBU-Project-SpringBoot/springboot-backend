package wiseSWlife.wiseSWlife.service.graduation.major;

import wiseSWlife.wiseSWlife.dto.graduation.form.MajorForm;

import java.util.ArrayList;

public interface Major {
    MajorForm getMajorForm(String sid, ArrayList<String>[] intranetMyMajorBegin, ArrayList<String>[] intranetMyMajorSelect, ArrayList<String>[] intranetMyMajorRequirement);

    int checkMajorBegin(ArrayList<String>[] intranetMyBeginMajor, ArrayList<String> myMajorBeginAndRequirementArr);

    int checkMajorRequirement(ArrayList<String>[] intranetMyRequirementMajor, ArrayList<String> myMajorBeginAndRequirementArr);

    int checkFutureDesign(ArrayList<String>[] intranetMyRequirementMajor);

    int checkMajorSelect(ArrayList<String>[] myMajorSelect, ArrayList<String> myMajorSelectArr);
}
