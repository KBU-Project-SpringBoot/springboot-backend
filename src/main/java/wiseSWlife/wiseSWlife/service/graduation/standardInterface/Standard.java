package wiseSWlife.wiseSWlife.service.graduation.standardInterface;

import wiseSWlife.wiseSWlife.model.graduation.form.CreditForm;
import wiseSWlife.wiseSWlife.model.graduation.form.GPAForm;

public interface Standard {

    CreditForm percentageGraduationCredit(int myCredit);

    GPAForm percentageGradationGPA(double myGPA);
}
