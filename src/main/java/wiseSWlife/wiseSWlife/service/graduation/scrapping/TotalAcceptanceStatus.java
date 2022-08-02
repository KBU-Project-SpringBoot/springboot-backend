package wiseSWlife.wiseSWlife.service.graduation.scrapping;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wiseSWlife.wiseSWlife.model.graduation.TotalAcceptanceStatusTable;

import java.io.*;

@Service
@RequiredArgsConstructor
public class TotalAcceptanceStatus {

    public TotalAcceptanceStatusTable scrapping(String intCookie) throws IOException, InterruptedException {
        String command = "C:\\Users\\User\\PycharmProjects\\pythonProjectVenv\\venv\\Scripts\\python.exe"; //명령어
        String arg1 = "C:\\Users\\User\\PycharmProjects\\pythonProjectVenv\\Wife_SW_Life\\login.py";//인지

        File testFile = new File(arg1);
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

        ProcessBuilder builder = new ProcessBuilder(command, arg1);
        Process process = builder.start();
        int exitVal = process.waitFor();//자식 프로세스가 종료될 때까지 기다림
        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), "euc-kr")); // 서브 프로세스가 출력하는 내용을 받기 위해
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
