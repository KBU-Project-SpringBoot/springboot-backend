package wiseSWlife.wiseSWlife.service.graduation.exam;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import wiseSWlife.wiseSWlife.db.repository.examRepository.ExamRepository;
import wiseSWlife.wiseSWlife.dto.graduation.ExamTable;
import wiseSWlife.wiseSWlife.dto.graduation.form.ExamForm;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExamImpl implements Exam {
    @Value("${python.engine}")
    String pythonEngine;

    @Value("${python.file}")
    String pythonFile;

    @Value("${python.encoding}")
    String pythonEncoding;

    public final static String ENGLISH = "영어";
    public final static String BIBLE = "성경";
    public final static String COMPUTER1 = "컴퓨터";
    public final static String COMPUTER2 = "컴퓨터2";
    public final static String PASS = "합격";
    public final static int PASS_COLUMN = 4;
    public final static int SUBJECT_COLUMN = 1;
    public final static String EXAM_NULL_EXCEPTION_MESSAGE = "Exam NULL 에러";

    private final ExamRepository examRepository;

    @Override
    public ExamForm exam(String sid, String intCookie) throws IOException, InterruptedException {
        String examApiResponse = getExamApiResponse(intCookie);

        if (examApiResponse == null) {
            throw new NullPointerException(EXAM_NULL_EXCEPTION_MESSAGE);
        }

        ExamForm examForm = convertStringToExamForm(sid, examApiResponse);
        saveExam(examForm);
        return examForm;
    }

    private String getExamApiResponse(String intCookie) throws IOException, InterruptedException {
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
        if (exitVal != 0) {//비정상적으로 종료시
            log.warn("서브 프로세스가 비정상 종료되었습니다.");
        }

        return br.readLine();
    }

    private ExamForm convertStringToExamForm(String sid, String examApiResponse) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        ExamTable examTable = gson.fromJson(examApiResponse, ExamTable.class);

        Map<String, Boolean> examMap = new HashMap<>();
        String[] subjects = {BIBLE, ENGLISH, COMPUTER1, COMPUTER2};

        for (String i : subjects) {
            examMap.put(i, false);
        }

        for (ArrayList i : examTable.getBody()) {
            if (i.get(PASS_COLUMN).equals(PASS)) {
                examMap.put(i.get(SUBJECT_COLUMN).toString(), true);
            }
        }

        ExamForm examForm = new ExamForm(sid, examMap.get(BIBLE), examMap.get(ENGLISH), examMap.get(COMPUTER1), examMap.get(COMPUTER2));

        return examForm;
    }

    private void saveExam(ExamForm examForm) {
        Optional<ExamForm> examBySid = examRepository.findExamBySid(examForm.getSid());
        if (examBySid.isEmpty()) {
            examRepository.save(examForm);
        }
        examRepository.update(examForm);
    }
}
