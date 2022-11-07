package wiseSWlife.wiseSWlife.db.repository.refinementRepo;

import wiseSWlife.wiseSWlife.model.graduation.form.RefinementForm;

import java.util.Optional;

public interface RefinementRepo {
    /**
     * 교양을 저장하는 기능(첫 스크래핑 시 사용)
     */
    RefinementForm save(RefinementForm refinementForm);

    /**
     * 교양 테이블을 수정하는 기능(스케줄링 시 사용)
     */
    RefinementForm update(RefinementForm refinementForm);

    /**
     * 학번으로 교양 테이블을 조회하는 기능
     */
    Optional<RefinementForm> findRefinementBySid(String sid);

}
