package wiseSWlife.wiseSWlife.dto.faqCenter;

import lombok.Data;

@Data
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
