package wiseSWlife.wiseSWlife.db.repository.faqCenterRepository;

import wiseSWlife.wiseSWlife.model.faqCenter.Faq;

import java.util.List;
import java.util.Optional;

public interface FaqRepository {

    // faq 저장
    Faq save(Faq faq);

    //sid을 통해 faq 찾기
    Optional<Faq> findBySid(String sid);

    // 등록 num으로 Faq 찾기
    Optional<Faq> findByNum(Long num);

    // 모든 FAQ 조회
    List<Faq> findAll();

    //FAQ 삭제
    void removeFaqByNum(Long num);

    //FAQ 수정
    Faq update(Long num, Faq UpdatedFaq);

    // 메모리 리셋
    default void clearStore(){ }

}
