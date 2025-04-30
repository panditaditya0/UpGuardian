package com.atozdev.uptimemoniter.repository.jobRepositoryy;

import com.atozdev.uptimemoniter.entity.Jobs.CertificateJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificateJobRepository extends JpaRepository<CertificateJob, Long> {
}
