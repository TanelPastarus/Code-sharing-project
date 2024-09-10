package platform;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CodeRepository extends CrudRepository<Code, Long> {
    List<Code> findTop10CodesByTimeRestrictedIsFalseAndViewRestrictedIsFalseOrderByDateDesc();
    Optional<Code> findById(UUID id);
}
