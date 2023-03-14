package wiseSWlife.wiseSWlife.db.repository.gpaRepository;

import wiseSWlife.wiseSWlife.dto.graduation.form.GPAForm;

import java.util.Optional;

public interface GPARepository {
    /**
     * 평균 평점을 저장하는 기능(첫 스크래핑 시 사용)
     */
    GPAForm save(GPAForm gpaForm);

    /**
     * 평균 평점 테이블을 수정하는 기능(스케줄링 시 사용)
     */
    GPAForm update(GPAForm gpaForm);

    /**
     * 학번으로 평균 평점 테이블을 조회하는 기능
     */
    Optional<GPAForm> findGPABySid(String sid);
}
