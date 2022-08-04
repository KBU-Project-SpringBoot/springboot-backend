package wiseSWlife.wiseSWlife.model.community.freeBoard.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class FreeBoardCommentForm {

    @NotNull
    @NotEmpty
    private String freeBoardComment;

    @NotNull
    @NotEmpty
    private String freeBoardCommentNickName;

}
