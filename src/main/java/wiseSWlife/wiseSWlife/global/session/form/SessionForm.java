package wiseSWlife.wiseSWlife.global.session.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class SessionForm {

    private String sid;

    private String name;

    private String major;

    private String intCookie;

}
