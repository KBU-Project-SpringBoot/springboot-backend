package wiseSWlife.wiseSWlife.service.graduation.basicCommonRequirementInf;

import wiseSWlife.wiseSWlife.dto.graduation.form.BCRForm;

import java.util.ArrayList;

public interface BasicCommonRequirement {
    BCRForm parse(String sid, ArrayList<String>[] myBasicCommonRequirement, ArrayList<String>[] myRefinementRequirement);
}
