package com.atozdev.uptimemoniter.repository.triggerRepository;

import com.atozdev.uptimemoniter.entity.Triggers.CertificateTrigger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificateTriggerRepository extends JpaRepository<CertificateTrigger, Long> {
}
