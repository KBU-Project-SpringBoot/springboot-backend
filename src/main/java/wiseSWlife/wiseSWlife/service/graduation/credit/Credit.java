package wiseSWlife.wiseSWlife.service.graduation.credit;

import wiseSWlife.wiseSWlife.dto.graduation.form.CreditForm;

public interface Credit {
    CreditForm getCredit(String sid, int myCredit);
}
