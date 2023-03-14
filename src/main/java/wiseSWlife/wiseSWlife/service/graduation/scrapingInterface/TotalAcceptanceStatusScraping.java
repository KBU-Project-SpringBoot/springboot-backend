package wiseSWlife.wiseSWlife.service.graduation.scrapingInterface;

import wiseSWlife.wiseSWlife.dto.graduation.TotalAcceptanceStatusTable;

import java.io.IOException;

public interface TotalAcceptanceStatusScraping {

    TotalAcceptanceStatusTable scrapping(String intCookie) throws IOException, InterruptedException;
}
