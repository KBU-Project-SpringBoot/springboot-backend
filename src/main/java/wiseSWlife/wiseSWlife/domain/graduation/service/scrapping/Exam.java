package wiseSWlife.wiseSWlife.domain.graduation.service.scrapping;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wiseSWlife.wiseSWlife.domain.graduation.ExamTable;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class Exam {

    public Map<String, Boolean> scrapping(String intCookie) throws IOException, InterruptedException {
        String command = "C:\\Users\\User\\PycharmProjects\\pythonProjectVenv\\venv\\Scripts\\python.exe"; //명령어
        String arg1 = "C:\\Users\\User\\PycharmProjects\\pythonProjectVenv\\Wife_SW_Life\\login.py";//인지

        File testFile = new File(arg1);
        FileWriter fw = new FileWriter(testFile);
        fw.write("import asyncio\n" + "\n");
        fw.write("from biblebot import IntranetAPI\n" + "\n");
        fw.write("async def main():\n");
        fw.write("    resp = await IntranetAPI.GraduationExam.fetch(cookies={'ASP.NET_SessionId': '" + intCookie + "'})\n");
        fw.write("    result = IntranetAPI.GraduationExam.parse(resp)\n");
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

        GsonBuilder builder1 = new GsonBuilder();
        builder1.setPrettyPrinting();
        Gson gson = builder1.create();

        ExamTable examTable = gson.fromJson(testLine,ExamTable.class);

        Map<String, Boolean> examMap = new HashMap<>();

        for(ArrayList i:examTable.getBody()){
            if(i.get(4).equals("합격자 신청 불가")){
                examMap.put(i.get(2).toString(),true);
            }
            else{
                examMap.put(i.get(2).toString(),false);
            }
        }
        return examMap;
    }

}
