package wiseSWlife.wiseSWlife.service.graduation.credit;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wiseSWlife.wiseSWlife.db.repository.totalCreditRepository.TotalCreditRepository;
import wiseSWlife.wiseSWlife.dto.graduation.form.CreditForm;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CreditImpl implements Credit{
    private final TotalCreditRepository totalCreditRepository;

    @Override
    public CreditForm getCredit(String sid, int myCredit){
        CreditForm creditForm = new CreditForm(sid, myCredit);

        Optional<CreditForm> totalCreditBySid = totalCreditRepository.findTotalCreditBySid(sid);
        if (totalCreditBySid.isEmpty()) {
            totalCreditRepository.save(creditForm);
        }else {
            totalCreditRepository.update(creditForm);
        }

        return creditForm;
    }
}
