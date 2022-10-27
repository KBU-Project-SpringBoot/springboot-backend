package wiseSWlife.wiseSWlife.service.graduation.standardInterface;

import wiseSWlife.wiseSWlife.model.graduation.form.CreditForm;
import wiseSWlife.wiseSWlife.model.graduation.form.GPAForm;
import wiseSWlife.wiseSWlife.model.graduation.form.MajorForm;
import wiseSWlife.wiseSWlife.model.graduation.form.RefinementForm;

import java.util.ArrayList;

public interface Standard {

    CreditForm checkCredit(int myCredit);

    GPAForm checkGPA(double myGPA);

    MajorForm checkMajor(ArrayList<String>[] myMajorBegin, ArrayList<String>[] myMajorSelect, ArrayList<String>[] myMajorRequirement);

    RefinementForm checkRefinement(ArrayList<String>[] myRefinementSelect, ArrayList<String>[] myRefinementRequirement);

}
