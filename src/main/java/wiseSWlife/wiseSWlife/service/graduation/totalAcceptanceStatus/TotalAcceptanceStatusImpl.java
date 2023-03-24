package wiseSWlife.wiseSWlife.service.graduation.totalAcceptanceStatus;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import wiseSWlife.wiseSWlife.dto.graduation.TotalAcceptanceStatusTable;

import java.io.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class TotalAcceptanceStatusImpl implements TotalAcceptanceStatus {
    @Value("${python.engine}")
    String pythonEngine;

    @Value("${python.file}")
    String pythonFile;

    @Value("${python.encoding}")
    String pythonEncoding;

    public final static String TOTAL_ACCEPT_STATUS_NULL_EXCEPTION_MESSAGE = "totalAcceptStatus NULL 에러";

    @Override
    public TotalAcceptanceStatusTable totalAcceptanceStatus(String intCookie) throws IOException, InterruptedException {
        String totalAcceptStatusApiResponse = getTotalAcceptanceStatusApiResponse(intCookie);

        if (totalAcceptStatusApiResponse == null) {
            throw new NullPointerException(TOTAL_ACCEPT_STATUS_NULL_EXCEPTION_MESSAGE);
        }

        TotalAcceptanceStatusTable totalAcceptanceStatusTable = convertStringToTotalAcceptStatusTable(totalAcceptStatusApiResponse);

        return totalAcceptanceStatusTable;
    }

    private String getTotalAcceptanceStatusApiResponse(String intCookie) throws IOException, InterruptedException {
        File testFile = new File(pythonFile);
        FileWriter fw = new FileWriter(testFile);
        fw.write("import asyncio\n" + "\n");
        fw.write("from biblebot import IntranetAPI\n" + "\n");
        fw.write("async def main():\n");
        fw.write("    resp = await IntranetAPI.TotalAcceptanceStatus.fetch(cookies={'ASP.NET_SessionId': '" + intCookie + "'})\n");
        fw.write("    result = IntranetAPI.TotalAcceptanceStatus.parse(resp)\n");
        fw.write("    responseData = dict(result.data.items())\n");
        fw.write("    print(responseData)\n");
        fw.write("asyncio.run(main())");
        fw.flush();
        fw.close();

        ProcessBuilder builder = new ProcessBuilder(pythonEngine, pythonFile);
        Process process = builder.start();
        int exitVal = process.waitFor();//자식 프로세스가 종료될 때까지 기다림
        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), pythonEncoding)); // 서브 프로세스가 출력하는 내용을 받기 위해
        if(exitVal != 0) {//비정상적으로 종료시
            log.warn("서브 프로세스가 비정상 종료되었습니다.");
        }
        return br.readLine();
    }

    private TotalAcceptanceStatusTable convertStringToTotalAcceptStatusTable(String totalAcceptStatusApiResponse) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();

        TotalAcceptanceStatusTable totalAcceptanceStatusTable = gson.fromJson(totalAcceptStatusApiResponse, TotalAcceptanceStatusTable.class);
        return totalAcceptanceStatusTable;
    }
}
