package recruitment_demand.domain.out;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import recruitment_demand.domain.RecruitmentDemand;

@Repository
public interface RecruitmentDemandRepository extends JpaRepository<RecruitmentDemand, Long> {
}
