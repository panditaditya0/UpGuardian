package com.atozdev.uptimemoniter.repository.triggerRepository;

import com.atozdev.uptimemoniter.entity.Triggers.ServiceTrigger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceTriggerRepository extends JpaRepository<ServiceTrigger, Long> {
}
