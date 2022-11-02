package wiseSWlife.wiseSWlife.service.graduation.scrapingInterface;

import wiseSWlife.wiseSWlife.model.graduation.ExamTable;
import wiseSWlife.wiseSWlife.model.graduation.form.ExamForm;

import java.io.IOException;
import java.util.Map;

public interface ExamScraping {

    ExamTable scraping(String intCookie) throws IOException, InterruptedException;

    ExamForm convert(String sid, ExamTable table);
}
