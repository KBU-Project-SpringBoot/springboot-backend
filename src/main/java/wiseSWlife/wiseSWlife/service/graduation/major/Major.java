package wiseSWlife.wiseSWlife.service.graduation.major;

import wiseSWlife.wiseSWlife.dto.graduation.form.MajorForm;

import java.util.ArrayList;

public interface Major {
    MajorForm checkMajor(String sid, ArrayList<String>[] myMajorBegin, ArrayList<String>[] myMajorSelect, ArrayList<String>[] myMajorRequirement);
}
