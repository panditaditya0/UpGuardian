package com.atozdev.uptimemoniter.repository.triggerRepository;

import com.atozdev.uptimemoniter.entity.Jobs.MainJobs;
import com.atozdev.uptimemoniter.entity.Triggers.ApiTrigger;
import com.atozdev.uptimemoniter.enums.IncidentLevel;
import com.atozdev.uptimemoniter.enums.JobStatus;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApiTriggerRepository extends JpaRepository<ApiTrigger, Long> {
    ApiTrigger findByJobTitle(String jobTitle);
    ApiTrigger findTopByOrderByTriggredAtDesc();
    ApiTrigger findTopByJobTitleOrderByTriggredAtDesc(String jobTitle);
    List<ApiTrigger> findTop5ByOrderByTriggredAtDesc();

    @Query("SELECT j FROM ApiTrigger j WHERE j.jobStatus = :status")
    List<ApiTrigger> findAllByJobStatusNot(@Param("status") JobStatus status);

    @Query("SELECT yt FROM ApiTrigger yt " +
            "WHERE yt.incidentLevel = :incidentLevel " +
            "AND yt.triggredAt = (" +
            "  SELECT MAX(sub.triggredAt) " +
            "  FROM ApiTrigger sub " +
            "  WHERE sub.jobTitle = yt.jobTitle " +
            "  AND sub.incidentLevel = :incidentLevel" +
            ")")
    List<ApiTrigger> findAllByIncidentLevel(@Param("incidentLevel") IncidentLevel incidentLevel);



    List<ApiTrigger> findAllByOrderByTriggredAtDesc(PageRequest pageRequest);
}
