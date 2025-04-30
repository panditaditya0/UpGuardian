package com.atozdev.uptimemoniter.repository.jobRepositoryy;

import com.atozdev.uptimemoniter.entity.Jobs.ServiceJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceJobRepository extends JpaRepository<ServiceJob, Long> {
}
