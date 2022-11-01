package wiseSWlife.wiseSWlife.service.graduation.scrapingInterface;

import wiseSWlife.wiseSWlife.model.graduation.ExamTable;

import java.io.IOException;
import java.util.Map;

public interface ExamScraping {

    ExamTable scraping(String intCookie) throws IOException, InterruptedException;

    Map<String, Boolean> convert(ExamTable table);
}
