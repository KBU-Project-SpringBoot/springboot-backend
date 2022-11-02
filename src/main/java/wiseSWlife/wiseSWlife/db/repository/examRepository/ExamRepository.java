package wiseSWlife.wiseSWlife.db.repository.examRepository;

import wiseSWlife.wiseSWlife.model.graduation.form.ExamForm;

import java.util.Map;

public interface ExamRepository {

    /**
     * 졸업 시험을 합/불 저장하는 기능(첫 스크래핑 시 사용)
     */
    ExamForm save(ExamForm examForm);

    /**
     * 졸업 시험 테이블을 수정하는 기능(스케줄링 시 사용)
     */
    ExamForm update(ExamForm examForm);

    /**
     * 졸업시험 전체를 조회하는 기능
     */

}
