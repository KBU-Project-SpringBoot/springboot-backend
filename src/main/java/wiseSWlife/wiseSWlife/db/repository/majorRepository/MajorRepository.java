package wiseSWlife.wiseSWlife.db.repository.majorRepository;

import wiseSWlife.wiseSWlife.model.graduation.form.MajorForm;

import java.util.Optional;

public interface MajorRepository {
    /**
     * 전공을 저장하는 기능(첫 스크래핑 시 사용)
     */
    MajorForm save(MajorForm majorForm);

    /**
     * 전공 테이블을 수정하는 기능(스케줄링 시 사용)
     */
    MajorForm update(MajorForm majorForm);

    /**
     * 학번으로 전공 테이블을 조회하는 기능
     */
    Optional<MajorForm> findMajorBySid(String sid);

}
