package wiseSWlife.wiseSWlife.service.graduation.condition;

import wiseSWlife.wiseSWlife.dto.graduation.form.CreditForm;
import wiseSWlife.wiseSWlife.dto.graduation.form.GPAForm;
import wiseSWlife.wiseSWlife.dto.graduation.form.MajorForm;
import wiseSWlife.wiseSWlife.dto.graduation.form.RefinementForm;

import java.util.ArrayList;

public interface Condition {

    CreditForm checkCredit(String sid, int myCredit);

    GPAForm checkGPA(String sid, double myGPA);

    MajorForm checkMajor(String sid, ArrayList<String>[] myMajorBegin, ArrayList<String>[] myMajorSelect, ArrayList<String>[] myMajorRequirement);

    RefinementForm checkRefinement(String sid, ArrayList<String>[] myRefinementSelect, ArrayList<String>[] myRefinementRequirement);

}
