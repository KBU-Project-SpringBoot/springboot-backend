package wiseSWlife.wiseSWlife.domain.graduation.service.standardInterface;

import wiseSWlife.wiseSWlife.domain.graduation.form.CreditForm;
import wiseSWlife.wiseSWlife.domain.graduation.form.GPAForm;

public interface Standard {

    CreditForm percentageGraduationCredit(int myCredit);

    GPAForm percentageGradationGPA(double myGPA);
}
