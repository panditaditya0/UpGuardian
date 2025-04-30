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

    @Query("SELECT j FROM ApiTrigger j WHERE j.incidentLevel = :incedentLevel")
    List<ApiTrigger> findAllByIncedent(IncidentLevel incedentLevel);

    List<ApiTrigger> findAllByOrderByTriggredAtDesc(PageRequest pageRequest);
}
