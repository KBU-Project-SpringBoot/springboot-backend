package wiseSWlife.wiseSWlife.db.repository.totalCreditRepository;

import wiseSWlife.wiseSWlife.model.graduation.form.CreditForm;

import java.util.Optional;

public interface TotalCreditRepository {
    /**
     * 총 이수 학점을 저장하는 기능(첫 스크래핑 시 사용)
     */
    CreditForm save(CreditForm creditForm);

    /**
     * 총 이수 학점 테이블을 수정하는 기능(스케줄링 시 사용)
     */
    CreditForm update(CreditForm creditForm);

    /**
     * 학번으로 총 이수 학점 테이블을 조회하는 기능
     */
    Optional<CreditForm> findTotalCreditBySid(String sid);

}
