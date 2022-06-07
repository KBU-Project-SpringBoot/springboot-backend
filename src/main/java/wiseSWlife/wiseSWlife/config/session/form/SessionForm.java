package wiseSWlife.wiseSWlife.config.session.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Data @Getter @Setter
public class SessionForm {

    private String sid;

    private String name;

    private String major;

    private String intCookie;

}
