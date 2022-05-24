package wiseSWlife.wiseSWlife.domain.faqCenter;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;



@Data
@Getter @Setter
public class Question {



    private long ManageNum;


    private String title;


    private String questionMember;

    private String question;

    private String answer;

    private String answerStatus;

    public Question(String title, String questionMember, String question) {
        this.title = title;
        this.questionMember = questionMember;
        this.question = question;
    }

}
