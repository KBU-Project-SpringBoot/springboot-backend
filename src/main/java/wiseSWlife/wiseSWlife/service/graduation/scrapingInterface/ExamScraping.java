package wiseSWlife.wiseSWlife.service.graduation.scrapingInterface;

import wiseSWlife.wiseSWlife.model.graduation.ExamTable;

import java.io.IOException;

public interface ExamScraping {

    ExamTable scrapping(String intCookie) throws IOException, InterruptedException;
}
