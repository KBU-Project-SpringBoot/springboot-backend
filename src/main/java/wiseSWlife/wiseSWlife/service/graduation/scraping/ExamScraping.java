package wiseSWlife.wiseSWlife.service.graduation.scraping;

import wiseSWlife.wiseSWlife.dto.graduation.ExamTable;
import wiseSWlife.wiseSWlife.dto.graduation.form.ExamForm;

import java.io.IOException;

public interface ExamScraping {

    ExamTable scraping(String intCookie) throws IOException, InterruptedException;

    ExamForm convert(String sid, ExamTable table);
}
