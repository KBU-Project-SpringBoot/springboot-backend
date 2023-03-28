package wiseSWlife.wiseSWlife.service.graduation.exam;

import wiseSWlife.wiseSWlife.dto.graduation.form.ExamForm;

import java.io.IOException;

public interface Exam {
    ExamForm exam(String sid, String intCookie) throws IOException, InterruptedException;
}
