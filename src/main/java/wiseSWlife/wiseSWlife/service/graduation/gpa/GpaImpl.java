package wiseSWlife.wiseSWlife.service.graduation.gpa;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wiseSWlife.wiseSWlife.dto.graduation.form.GPAForm;

@Service
@RequiredArgsConstructor
public class GpaImpl implements Gpa {
    @Override
    public GPAForm checkGPA(String sid, double myGPA){
        GPAForm gpaForm = new GPAForm(sid, myGPA);

        return gpaForm;
    }
}
