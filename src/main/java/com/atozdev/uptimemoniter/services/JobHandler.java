package com.atozdev.uptimemoniter.services;

import com.atozdev.uptimemoniter.dtos.AlertCardItemDto;
import com.atozdev.uptimemoniter.dtos.DashboardCardDto;
import com.atozdev.uptimemoniter.dtos.HeartbeatCardItemDto;
import com.atozdev.uptimemoniter.entity.Jobs.MainJobs;
import com.atozdev.uptimemoniter.enums.IncidentLevel;
import com.atozdev.uptimemoniter.enums.JobStatus;
import com.atozdev.uptimemoniter.enums.JobType;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Map;

public interface JobHandler {
    JobType getJobType();
    void saveJob(Map<String, String> newjob);
    Object getJobsHistory();
    Object getCurrentStatus();
    boolean updateJob(Map<String, String> dto, String oldJobTitle);
    boolean delete(MainJobs dto);
    DashboardCardDto fetchAllJobsTitles();
    Object fetchJobsByStatus(JobStatus status);
    List<AlertCardItemDto> fetchJobsByIncicentLevel(IncidentLevel incedentLevel);

    List<HeartbeatCardItemDto> fetchAllHeartBeats(PageRequest count);
}
