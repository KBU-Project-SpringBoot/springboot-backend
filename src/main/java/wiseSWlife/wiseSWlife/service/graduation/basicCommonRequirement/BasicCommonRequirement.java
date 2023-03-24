package wiseSWlife.wiseSWlife.service.graduation.basicCommonRequirement;

import wiseSWlife.wiseSWlife.dto.graduation.form.BCRForm;

import java.util.ArrayList;
import java.util.HashMap;

public interface BasicCommonRequirement {
    BCRForm getBCR(String sid, ArrayList<String>[] myBasicCommonRequirement, ArrayList<String>[] myRefinementRequirement);

    void checkBCR(ArrayList<String>[] myBasicCommonRequirement, ArrayList<String>[] myRefinementRequirement, HashMap<String, Integer> basicCommonRequirementMap);
}
