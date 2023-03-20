package wiseSWlife.wiseSWlife.service.graduation.refinement;

import wiseSWlife.wiseSWlife.dto.graduation.form.RefinementForm;

import java.util.ArrayList;

public interface Refinement {
    RefinementForm checkRefinement(String sid, ArrayList<String>[] myRefinementSelect, ArrayList<String>[] myRefinementRequirement);
}
