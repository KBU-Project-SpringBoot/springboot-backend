package wiseSWlife.wiseSWlife.service.graduation.scraping;

import wiseSWlife.wiseSWlife.dto.graduation.TotalAcceptanceStatusTable;

import java.io.IOException;

public interface TotalAcceptanceStatusScraping {

    TotalAcceptanceStatusTable scrapping(String intCookie) throws IOException, InterruptedException;
}
