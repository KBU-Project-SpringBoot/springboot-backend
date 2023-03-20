package wiseSWlife.wiseSWlife.service.graduation.credit;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wiseSWlife.wiseSWlife.dto.graduation.form.CreditForm;

@Service
@RequiredArgsConstructor
public class CreditImpl implements Credit{
    @Override
    public CreditForm checkCredit(String sid, int myCredit){
        CreditForm creditForm = new CreditForm(sid, myCredit);

        return creditForm;
    }
}
