package com.atozdev.uptimemoniter.repository.triggerRepository;

import com.atozdev.uptimemoniter.entity.Triggers.ServerTrigger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServerTriggerRepository extends JpaRepository<ServerTrigger, Long> {
}
