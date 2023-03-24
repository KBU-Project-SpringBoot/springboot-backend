package wiseSWlife.wiseSWlife.service.graduation.gpa;

import wiseSWlife.wiseSWlife.dto.graduation.form.GPAForm;

public interface Gpa {
    GPAForm getGpa(String sid, double myGPA);
}
