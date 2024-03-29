package wiseSWlife.wiseSWlife.service.login;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import wiseSWlife.wiseSWlife.db.repository.intranetRepository.IntranetRepository;
import wiseSWlife.wiseSWlife.db.repository.memberRepository.MemberRepository;
import wiseSWlife.wiseSWlife.dto.intranet.Intranet;
import wiseSWlife.wiseSWlife.dto.member.Member;

import java.io.*;
import java.sql.SQLException;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    @Value("${python.engine}")
    String pythonEngine;

    @Value("${python.file}")
    String pythonFile;

    @Value("${python.encoding}")
    String pythonEncoding;

    private final MemberRepository memberRepository;
    private final IntranetRepository intranetRepository;

    @Override
    public Member login(String loginId, String password) throws IOException, InterruptedException, SQLException {
        String loginApiResponse = getLoginApiResponse(loginId, password);

        if (loginApiResponse == null) {
            throw new NullPointerException("Login 정보가 없습니다.");
        }

        Member member = convertStringToMember(loginApiResponse);
        saveMember(member);
        saveIntranet(member, loginId, password);

        return member;
    }

    private String getLoginApiResponse(String loginId, String password) throws IOException, InterruptedException {
        File testFile = new File(pythonFile);
        FileWriter fw = new FileWriter(testFile);
        fw.write("import asyncio\n" + "\n");
        fw.write("from biblebot import IntranetAPI\n" + "\n");
        fw.write("async def main():\n");
        fw.write("    resp = await IntranetAPI.Login.fetch(\"" + loginId + "\", \"" + password + "\")\n");
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
        if (exitVal != 0) {//비정상적으로 종료시
            log.warn("서브 프로세스가 비정상 종료되었습니다.");
        }

        return br.readLine();
    }

    private Member convertStringToMember(String loginApiResponse) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        Member member = gson.fromJson(loginApiResponse, Member.class);
        return member;
    }

    private void saveMember(Member member) throws SQLException {
        Optional<Member> bySid = memberRepository.findBySid(member.getSid());
        if (bySid.isEmpty()) {
            memberRepository.save(member);
        }
        memberRepository.update(member);
    }

    private void saveIntranet(Member member, String loginId, String password) {
        Intranet loginIntranet = new Intranet(member.getSid(), loginId, password);
        Optional<Intranet> byIntranetId = intranetRepository.findByIntranetId(loginId);
        if (byIntranetId.isEmpty()) {
            intranetRepository.save(loginIntranet);
        }
        intranetRepository.update(loginIntranet);
    }
}
