package wiseSWlife.wiseSWlife.service.graduation.standardInterface;

import wiseSWlife.wiseSWlife.model.graduation.form.CreditForm;
import wiseSWlife.wiseSWlife.model.graduation.form.GPAForm;
import wiseSWlife.wiseSWlife.model.graduation.form.MajorForm;

import java.util.ArrayList;

public interface Standard {

    CreditForm percentageGraduationCredit(int myCredit);

    GPAForm percentageGradationGPA(double myGPA);

    MajorForm checkMajor(ArrayList<String>[] myMajorBegin, ArrayList<String>[] myMajorSelect, ArrayList<String>[] myMajorRequirement);
}
