package com.atozdev.uptimemoniter.services.JobImpl;

import com.atozdev.uptimemoniter.dtos.AlertCardItemDto;
import com.atozdev.uptimemoniter.dtos.DashboardCardDto;
import com.atozdev.uptimemoniter.dtos.DashboardCardJobStatusDto;
import com.atozdev.uptimemoniter.dtos.HeartbeatCardItemDto;
import com.atozdev.uptimemoniter.entity.Jobs.MainJobs;
import com.atozdev.uptimemoniter.enums.IncidentLevel;
import com.atozdev.uptimemoniter.enums.JobStatus;
import com.atozdev.uptimemoniter.enums.JobType;
import com.atozdev.uptimemoniter.services.JobHandler;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ServiceMoniterJob implements Job, JobHandler {
    @Override
    public JobType getJobType() {
        return JobType.SERVICE;
    }

    @Override
    public void saveJob(Map<String, String> newjob) {

    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

    }

    @Override
    public Object getJobsHistory() {
        return null;
    }

    @Override
    public Object getCurrentStatus() {
        return null;
    }

    @Override
    public boolean updateJob(Map<String, String> dto, String oldJobTitle) {
        return false;
    }

    @Override
    public boolean delete(MainJobs dto) {
        return false;
    }

    @Override
    public DashboardCardDto fetchAllJobsTitles() {
        List<DashboardCardJobStatusDto> allJobStatus = new ArrayList<DashboardCardJobStatusDto>();

        return new DashboardCardDto(
                this.getJobType().getJobName(),
                this.getJobType().getJobIcon(),
                this.getJobType(),
                allJobStatus);
    }

    @Override
    public Object fetchJobsByStatus(JobStatus status) {
        return null;
    }

    @Override
    public List<AlertCardItemDto> fetchJobsByIncicentLevel(IncidentLevel incedentLevel) {
        return new ArrayList<AlertCardItemDto>();
    }

    @Override
    public List<HeartbeatCardItemDto> fetchAllHeartBeats(PageRequest count) {
        return new ArrayList<HeartbeatCardItemDto>();
    }
    @Override
    public boolean validateNewJobForm(Map<String, String> newJob) {
        return false;
    }
}