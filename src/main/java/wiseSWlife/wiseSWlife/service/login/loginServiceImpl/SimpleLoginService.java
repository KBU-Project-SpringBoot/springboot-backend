package wiseSWlife.wiseSWlife.service.login.loginServiceImpl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import wiseSWlife.wiseSWlife.service.login.loginServiceInterface.LoginService;
import wiseSWlife.wiseSWlife.dto.member.Member;

import java.io.*;

@Service
public class SimpleLoginService implements LoginService {
    @Value("${python.engine}")
    String pythonEngine;

    @Value("${python.file}")
    String pythonFile;

    @Value("${python.encoding}")
    String pythonEncoding;

    @Override
    public Member login(String loginId, String password) throws IOException, InterruptedException {
        File testFile = new File(pythonFile);
        FileWriter fw = new FileWriter(testFile);
        fw.write("import asyncio\n" + "\n");
        fw.write("from biblebot import IntranetAPI\n" + "\n");
        fw.write("async def main():\n");
        fw.write("    resp = await IntranetAPI.Login.fetch(\"" + loginId +"\", \"" + password + "\")\n");
        fw.write("    result = IntranetAPI.Login.parse(resp)\n");
        fw.write("    cookie = result.data[\"cookies\"]\n");
        fw.write("    resp = await IntranetAPI.Profile.fetch(cookies=cookie)\n");
        fw.write("    result = IntranetAPI.Profile.parse(resp)\n");
        fw.write("    responseData = dict(cookie.items()|result.data.items())\n");
        fw.write("    responseData[\"intCookie\"] = responseData.pop(\"ASP.NET_SessionId\")\n");
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

        String testLine = br.readLine();//파이썬에서 나온 객체모양 문자열
        //System.out.println(testLine);//데이터 유형 : 로그인 성공시 ->{세션, 학번, 이름, 학과}
                                                         //실패시 null

        if(testLine == null){
            return null;
        }

        GsonBuilder builder1 = new GsonBuilder();
        builder1.setPrettyPrinting();
        Gson gson = builder1.create();
        Member member = gson.fromJson(testLine,Member.class);

//        System.out.println("너는 누구니"+member);//member 객체
//
//        testLine = gson.toJson(member);//json으로 변경
//        System.out.println("얘는 뭐지"+testLine);//json형


        return member;


    }
}
