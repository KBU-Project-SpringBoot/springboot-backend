package wiseSWlife.wiseSWlife.service.graduation.scrapingInterface;

import wiseSWlife.wiseSWlife.model.graduation.TotalAcceptanceStatusTable;

import java.io.IOException;

public interface TotalAcceptanceStatusScraping {

    TotalAcceptanceStatusTable scrapping(String intCookie) throws IOException, InterruptedException;
}
