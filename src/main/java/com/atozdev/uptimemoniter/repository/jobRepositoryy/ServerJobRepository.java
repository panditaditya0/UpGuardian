package com.atozdev.uptimemoniter.repository.jobRepositoryy;

import com.atozdev.uptimemoniter.entity.Jobs.ApiJob;
import com.atozdev.uptimemoniter.entity.Jobs.ServerJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServerJobRepository extends JpaRepository<ServerJob, Long> {
    ServerJob getByJobTitle(String jobName);
}
