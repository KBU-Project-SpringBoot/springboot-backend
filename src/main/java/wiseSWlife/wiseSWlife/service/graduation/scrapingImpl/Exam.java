package wiseSWlife.wiseSWlife.service.graduation.scrapingImpl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import wiseSWlife.wiseSWlife.model.graduation.ExamTable;
import wiseSWlife.wiseSWlife.model.graduation.form.ExamForm;
import wiseSWlife.wiseSWlife.service.graduation.scrapingInterface.ExamScraping;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class Exam implements ExamScraping {
    @Value("${python.engine}")
    String pythonEngine;

    @Value("${python.file}")
    String pythonFile;

    @Value("${python.encoding}")
    String pythonEncoding;

    @Override
    public ExamTable scraping(String intCookie) throws IOException, InterruptedException {
        File testFile = new File(pythonFile);
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

        ProcessBuilder builder = new ProcessBuilder(pythonEngine, pythonFile);
        Process process = builder.start();
        int exitVal = process.waitFor();//자식 프로세스가 종료될 때까지 기다림
        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), pythonEncoding)); // 서브 프로세스가 출력하는 내용을 받기 위해
        if(exitVal != 0) {//비정상적으로 종료시
            System.out.println("서브 프로세스가 비정상 종료되었습니다.");
        }

        String testLine = br.readLine();
        GsonBuilder builder1 = new GsonBuilder();
        builder1.setPrettyPrinting();
        Gson gson = builder1.create();

        ExamTable examTable = gson.fromJson(testLine,ExamTable.class);

        return examTable;
    }

    @Override
    public ExamForm convert(String sid, ExamTable table) {
        Map<String, Boolean> examMap = new HashMap<>();
        String[] subjects = {"성경", "영어", "컴퓨터", "컴퓨터2"};

        for(String i : subjects){
            examMap.put(i, false);
        }

        for(ArrayList i:table.getBody()){
            if(i.get(4).equals("합격")){
                examMap.put(i.get(1).toString(),true);
            }
        }

        ExamForm examForm = new ExamForm(sid, examMap.get("성경"), examMap.get("영어"), examMap.get("컴퓨터"), examMap.get("컴퓨터2"));

        return examForm;
    }
}
