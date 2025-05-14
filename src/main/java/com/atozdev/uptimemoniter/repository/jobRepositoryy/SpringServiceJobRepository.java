package com.atozdev.uptimemoniter.repository.jobRepositoryy;

import com.atozdev.uptimemoniter.entity.Jobs.ApiJob;
import com.atozdev.uptimemoniter.entity.Jobs.ServiceJob;
import com.atozdev.uptimemoniter.entity.Jobs.SpringServiceJob;
import com.atozdev.uptimemoniter.entity.Triggers.ApiTrigger;
import com.atozdev.uptimemoniter.services.JobImpl.SpringServiceMoniterJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringServiceJobRepository extends JpaRepository<SpringServiceJob, Long> {
    SpringServiceJob findByJobTitle(String jobTitle);

}
