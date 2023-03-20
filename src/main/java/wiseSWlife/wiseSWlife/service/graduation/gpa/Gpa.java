package wiseSWlife.wiseSWlife.service.graduation.gpa;

import wiseSWlife.wiseSWlife.dto.graduation.form.GPAForm;

public interface Gpa {
    GPAForm checkGPA(String sid, double myGPA);
}
