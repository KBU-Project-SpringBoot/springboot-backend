package wiseSWlife.wiseSWlife.db.repository.intranetRepository;

import wiseSWlife.wiseSWlife.dto.intranet.Intranet;

import java.util.List;
import java.util.Optional;

public interface IntranetRepository {
    Intranet save(Intranet intranet);

    Intranet update(Intranet intranet);

    Optional<Intranet> findByIntranetId(String intranetId);

    List<Intranet> findAll();
}
