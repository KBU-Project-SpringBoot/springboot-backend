package wiseSWlife.wiseSWlife.service.graduation.totalAcceptanceStatus;

import wiseSWlife.wiseSWlife.dto.graduation.TotalAcceptanceStatusTable;

import java.io.IOException;

public interface TotalAcceptanceStatus {

    TotalAcceptanceStatusTable scrapping(String intCookie) throws IOException, InterruptedException;
}
