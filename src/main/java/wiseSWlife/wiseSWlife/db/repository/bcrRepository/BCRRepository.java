package wiseSWlife.wiseSWlife.db.repository.bcrRepository;

import wiseSWlife.wiseSWlife.dto.graduation.form.BCRForm;

import java.util.Optional;

public interface BCRRepository {
    /**
     * 기초공통필수을 저장하는 기능(첫 스크래핑 시 사용)
     */
    BCRForm save(BCRForm bcrForm);

    /**
     * 기초공통필수 테이블을 수정하는 기능(스케줄링 시 사용)
     */
    BCRForm update(BCRForm bcrForm);

    /**
     * 학번으로 기초공통필수 테이블을 조회하는 기능
     */
    Optional<BCRForm> findBCRBySid(String sid);
}
