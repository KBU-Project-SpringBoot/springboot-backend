package wiseSWlife.wiseSWlife.service.graduation.standardImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import wiseSWlife.wiseSWlife.model.graduation.form.CreditForm;
import wiseSWlife.wiseSWlife.model.graduation.form.GPAForm;
import wiseSWlife.wiseSWlife.service.graduation.standardInterface.Standard;


@Service
@RequiredArgsConstructor
public class Standard2017 implements Standard {

    @Value("${totalCredit.2017}")
    String totalCredit;//졸업학점

    @Value("${totalGPA.2017}")
    String totalGPA;//졸업기준 평균평점

    @Override
    public CreditForm percentageGraduationCredit(int myCredit){
        double creditPercentage = (double) myCredit / Integer.parseInt(totalCredit) * 100.0;
        String formatingPercent = String.format("%.2f",creditPercentage);

        CreditForm creditForm = new CreditForm(Integer.parseInt(totalCredit),myCredit,formatingPercent);

        return creditForm;
    }

    @Override
    public GPAForm percentageGradationGPA(double myGPA){
        String resultGPA = (myGPA > Double.parseDouble(totalGPA)) ? "공부를 열심히 하셨네요!" : "공부를 얼마나 안한거야...?";

        GPAForm gpaForm = new GPAForm(Double.parseDouble(totalGPA), myGPA,resultGPA);

        return gpaForm;
    }

}
