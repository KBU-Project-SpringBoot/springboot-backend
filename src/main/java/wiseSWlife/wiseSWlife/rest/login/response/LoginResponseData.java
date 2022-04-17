package wiseSWlife.wiseSWlife.rest.login.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Data
@Getter @AllArgsConstructor
public class LoginResponseData {
    // login response json
    private HttpStatus status;

    private String path;

    private String message;

    private String loginId;

    private String sessionId;

}
