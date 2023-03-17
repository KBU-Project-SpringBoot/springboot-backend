package wiseSWlife.wiseSWlife.db.repository.faqCenterRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import wiseSWlife.wiseSWlife.dto.faqCenter.Question;

import java.util.*;
@Slf4j
@Repository
public class QuestionRepositoryImpl implements QuestionRepository {

    private static Map<Long, Question> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Question save(Question question) {
        question.setManageNum(++sequence);
        store.put(question.getManageNum(), question);
        log.info("question 저장 완료 question[{}]=",question);
        return question;
    }

    @Override
    public Optional<Question> findQuestionBySid(String sid) {
        return findAll().stream()
                .filter(q -> q.getQuestionMember().equals(sid))
                .findFirst();
    }

    @Override
    public Optional<Question> findQuestionByNum(Long mangeNum) {
        return findAll().stream()
                .filter(q -> q.getManageNum()==mangeNum)
                .findFirst();
    }

    @Override
    public List<Question> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void removeQuestionByNum(Long mangeNum) {
        store.remove(mangeNum);
    }

    @Override
    public Question update(Long mangeNum, Question questionParam) {
        Optional<Question> questionByNum = findQuestionByNum(mangeNum);
        if(questionByNum == null){
            return null;
        }
        Question question = questionByNum.get();

        question.setQuestionMember(questionParam.getQuestionMember());
        question.setTitle(questionParam.getTitle());
        question.setQuestion(questionParam.getQuestion());
        question.setAnswer(questionParam.getAnswer());
        return question;
    }

    @Override //for test
    public void clearStore() {
        store.clear();
    }
}
