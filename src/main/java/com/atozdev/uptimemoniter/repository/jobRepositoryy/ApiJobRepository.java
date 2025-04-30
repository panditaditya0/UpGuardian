package com.atozdev.uptimemoniter.repository.jobRepositoryy;

import com.atozdev.uptimemoniter.entity.Jobs.ApiJob;
import com.atozdev.uptimemoniter.entity.Triggers.ApiTrigger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiJobRepository extends JpaRepository<ApiJob, Long> {
    ApiJob findByJobTitle(String jobTitle);
}
