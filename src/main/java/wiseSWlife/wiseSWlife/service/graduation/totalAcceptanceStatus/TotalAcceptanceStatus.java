package wiseSWlife.wiseSWlife.service.graduation.totalAcceptanceStatus;

import wiseSWlife.wiseSWlife.dto.graduation.TotalAcceptanceStatusTable;

import java.io.IOException;

public interface TotalAcceptanceStatus {

    TotalAcceptanceStatusTable totalAcceptanceStatus(String intCookie) throws IOException, InterruptedException;
}
