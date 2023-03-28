package wiseSWlife.wiseSWlife.service.graduation.refinement;

import wiseSWlife.wiseSWlife.dto.graduation.form.RefinementForm;

import java.util.ArrayList;
import java.util.HashMap;

public interface Refinement {
    RefinementForm getRefinementForm(String sid, ArrayList<String>[] myRefinementSelect, ArrayList<String>[] myRefinementRequirement);

    void checkRefinementSelect(ArrayList<String>[] myRefinementSelect, HashMap<String, Integer> refinementMap, ArrayList<String> myRefinementArr);

    void checkRefinementRequirement(ArrayList<String>[] myRefinementRequirement, HashMap<String, Integer> refinementMap, ArrayList<String> myEnglishArr, ArrayList<String> myRefinementArr);
}
