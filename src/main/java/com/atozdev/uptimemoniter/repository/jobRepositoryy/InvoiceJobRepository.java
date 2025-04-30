package com.atozdev.uptimemoniter.repository.jobRepositoryy;

import com.atozdev.uptimemoniter.entity.Jobs.InvoiceJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceJobRepository extends JpaRepository<InvoiceJob, Long> {
}
