package wiseSWlife.wiseSWlife.db.repository.examRepository;

import wiseSWlife.wiseSWlife.dto.graduation.form.ExamForm;

import java.util.List;
import java.util.Optional;

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
    List<ExamForm> findAll();

    /**
     * 학번으로 졸업시험을 조회하는 기능
     */
    Optional<ExamForm> findExamBySid(String sid);

}
