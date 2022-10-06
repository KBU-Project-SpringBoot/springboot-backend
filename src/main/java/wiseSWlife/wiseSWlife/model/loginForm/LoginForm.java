package wiseSWlife.wiseSWlife.model.loginForm;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter @Setter @AllArgsConstructor
public class LoginForm {

    @NotEmpty
    private String loginId;
    @NotEmpty
    private String password;

}
