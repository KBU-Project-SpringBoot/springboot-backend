package wiseSWlife.wiseSWlife.db.entity.faqCenterRepository;

import org.springframework.stereotype.Repository;
import wiseSWlife.wiseSWlife.model.faqCenter.Faq;
import wiseSWlife.wiseSWlife.db.repository.faqCenterRepository.FaqRepository;

import java.util.*;

@Repository
public class FaqRepositoryImpl implements FaqRepository {

    private static Map<Long, Faq> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Faq save(Faq faq) {
        faq.setNum(++sequence);
        store.put(faq.getNum(), faq);
        return faq;
    }

    @Override
    public Optional<Faq> findBySid(String sid) {
        return findAll().stream()
                .filter(f -> f.getQuestionMember().equals(sid))
                .findFirst();
    }

    @Override
    public  Optional<Faq> findByNum(Long num) {

        return findAll().stream()
                .filter(f -> f.getNum()==num)
                .findFirst();
    }

    @Override
    public List<Faq> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void removeFaqByNum(Long num) {
        store.remove(num);
    }

    @Override
    public Faq update(Long num, Faq updatedFaq) {
        Optional<Faq> findFaq = findByNum(num);
        if(findFaq==null){
            return null;
        }

        Faq faq = findFaq.get();

        faq.setQuestionMember(updatedFaq.getQuestionMember());
        faq.setTitle(updatedFaq.getTitle());
        faq.setQuestion(updatedFaq.getQuestion());
        faq.setAnswer(updatedFaq.getAnswer());

        return faq;
    }

    @Override // for test
    public void clearStore() {
    store.clear();
    }
}
