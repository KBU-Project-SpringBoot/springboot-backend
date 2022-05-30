package wiseSWlife.wiseSWlife.rest.signUp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import wiseSWlife.wiseSWlife.domain.member.Member;
import wiseSWlife.wiseSWlife.domain.repositoryInterface.memberRepository.MemberRepository;
import wiseSWlife.wiseSWlife.rest.signUp.form.SignUpForm;
import wiseSWlife.wiseSWlife.rest.signUp.response.SignUpResponseData;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SignUpController {

    private final MemberRepository memberRepository;

    @RequestMapping("/signUp")
    @ResponseBody
    public SignUpResponseData signUp(@RequestBody SignUpForm form, HttpServletRequest request) {
        Optional<Member> signUpMem = memberRepository.findByLoginId(form.getLoginId());
        if (signUpMem.isPresent()) {
            log.info("회원 가입 실패 [이미 존재하는 ID = {}]", form.getLoginId());
            return new SignUpResponseData(HttpStatus.BAD_REQUEST, request.getRequestURI(), "이미 존재하는 ID입니다.", form.getLoginId());
        }
        memberRepository.save(new Member(form.getLoginId(), form.getPassword(), form.getNickName()));
        log.info("회원 가입 완료 {} , {}", form.getLoginId(), form.getPassword());
        return new SignUpResponseData(HttpStatus.OK, request.getRequestURI(), "SignUp Success", form.getLoginId());
    }
}
