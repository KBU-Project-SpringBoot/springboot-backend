package wiseSWlife.wiseSWlife.web.controller.community.freeBoard.form;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class FreeBoardPostForm {

    @NotEmpty
    @NotNull
    private String freeBoardPostName;

    @NotEmpty
    @NotNull
    private String freeBoardTitle;

    @NotEmpty
    @NotNull
    private String freeBoardText;

}
