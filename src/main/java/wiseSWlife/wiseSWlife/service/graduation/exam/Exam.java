package wiseSWlife.wiseSWlife.service.graduation.exam;

import wiseSWlife.wiseSWlife.dto.graduation.ExamTable;
import wiseSWlife.wiseSWlife.dto.graduation.form.ExamForm;

import java.io.IOException;

public interface Exam {

    ExamTable scraping(String intCookie) throws IOException, InterruptedException;

    ExamForm convert(String sid, ExamTable table);
}
