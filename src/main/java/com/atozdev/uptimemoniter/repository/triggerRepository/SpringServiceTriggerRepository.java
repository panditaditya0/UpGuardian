package com.atozdev.uptimemoniter.repository.triggerRepository;

import com.atozdev.uptimemoniter.entity.Triggers.ApiTrigger;
import com.atozdev.uptimemoniter.entity.Triggers.ServiceTrigger;
import com.atozdev.uptimemoniter.entity.Triggers.SpringServiceTrigger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SpringServiceTriggerRepository extends JpaRepository<SpringServiceTrigger, Long> {
    SpringServiceTrigger findTopByJobTitleOrderByTriggredAtDesc(String jobTitle);

    @Query("SELECT t FROM SpringServiceTrigger t WHERE t.triggredAt >= :fromTime AND t.jobTitle = :jobTitle ORDER BY t.triggredAt DESC")
    List<SpringServiceTrigger> findTriggersSince(@Param("fromTime") LocalDateTime fromTime, @Param("jobTitle") String jobTitle);
}
