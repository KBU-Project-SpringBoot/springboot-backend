package wiseSWlife.wiseSWlife.service.graduation.credit;

import wiseSWlife.wiseSWlife.dto.graduation.form.CreditForm;

public interface Credit {
    CreditForm checkCredit(String sid, int myCredit);
}
