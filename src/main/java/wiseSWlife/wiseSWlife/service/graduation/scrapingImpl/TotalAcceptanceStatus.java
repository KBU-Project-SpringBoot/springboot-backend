package wiseSWlife.wiseSWlife.service.graduation.scrapingImpl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import wiseSWlife.wiseSWlife.model.graduation.TotalAcceptanceStatusTable;

import java.io.*;

@Service
@RequiredArgsConstructor
public class TotalAcceptanceStatus {
    @Value("${python.engine}")
    String pythonEngine;

    @Value("${python.file}")
    String pythonFile;

    @Value("${python.encoding}")
    String pythonEncoding;

    public TotalAcceptanceStatusTable scrapping(String intCookie) throws IOException, InterruptedException {
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
            System.out.println("서브 프로세스가 비정상 종료되었습니다.");
        }

        String testLine = br.readLine();
        System.out.println(testLine);

        GsonBuilder builder1 = new GsonBuilder();
        builder1.setPrettyPrinting();
        Gson gson = builder1.create();

        TotalAcceptanceStatusTable totalAcceptanceStatusTable = gson.fromJson(testLine, TotalAcceptanceStatusTable.class);


        return totalAcceptanceStatusTable;
    }
}