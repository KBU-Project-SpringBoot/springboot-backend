package wiseSWlife.wiseSWlife.rest.signUp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import wiseSWlife.wiseSWlife.domain.member.Member;
import wiseSWlife.wiseSWlife.domain.repositoryInterface.MemberRepository;
import wiseSWlife.wiseSWlife.rest.signUp.form.SignUpForm;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SignUpController {

    private final MemberRepository memberRepository;

    @PostMapping("/signUp")
    @ResponseBody
    public Map signUp(@RequestBody SignUpForm form){
        Map<String, String> result = new HashMap<>();
        if(!memberRepository.findByLoginId(form.getUsername()).isEmpty()){
            result.put("message","이미 존재하는 ID입니다.");
            result.put("status","400");
        }else{
            memberRepository.save(new Member(form.getUsername(),form.getPassword()));
            log.info("회원 가입 완료 {} , {}",form.getUsername(), form.getPassword());
            result.put("message","로그인 성공");
            result.put("status","200");
        }
        return result;
    }
}
