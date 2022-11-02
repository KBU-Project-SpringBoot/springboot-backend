package wiseSWlife.wiseSWlife.db.repository.examRepository;

import java.util.Map;

public interface ExamRepository {

    /**
     * 졸업 시험을 합/불 저장하는 기능(첫 스크래핑 시 사용)
     */
    Map<String, Boolean> save(Map<String, Boolean> examMap);

    /**
     * 졸업 시험 테이블을 수정하는 기능
     */

    /**
     * 졸업시험 전체를 조회하는 기능
     */

}
