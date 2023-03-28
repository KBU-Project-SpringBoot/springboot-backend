package wiseSWlife.wiseSWlife.service.graduation.gpa;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wiseSWlife.wiseSWlife.db.repository.gpaRepository.GPARepository;
import wiseSWlife.wiseSWlife.dto.graduation.form.GPAForm;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GpaImpl implements Gpa {
    private final GPARepository gpaRepository;

    @Override
    public GPAForm getGpa(String sid, double myGPA) {
        GPAForm gpaForm = new GPAForm(sid, myGPA);
        Optional<GPAForm> gpaBySid = gpaRepository.findGPABySid(sid);
        if (gpaBySid.isEmpty()) {
            gpaRepository.save(gpaForm);
        } else {
            gpaRepository.update(gpaForm);
        }
        return gpaForm;
    }
}
