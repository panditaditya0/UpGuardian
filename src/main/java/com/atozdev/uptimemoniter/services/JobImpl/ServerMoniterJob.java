package com.atozdev.uptimemoniter.services.JobImpl;

import com.atozdev.uptimemoniter.dtos.AlertCardItemDto;
import com.atozdev.uptimemoniter.dtos.DashboardCardDto;
import com.atozdev.uptimemoniter.dtos.DashboardCardJobStatusDto;
import com.atozdev.uptimemoniter.dtos.HeartbeatCardItemDto;
import com.atozdev.uptimemoniter.entity.Jobs.MainJobs;
import com.atozdev.uptimemoniter.entity.Jobs.ServerJob;
import com.atozdev.uptimemoniter.entity.Triggers.ServerTrigger;
import com.atozdev.uptimemoniter.enums.IncidentLevel;
import com.atozdev.uptimemoniter.enums.JobStatus;
import com.atozdev.uptimemoniter.enums.JobType;
import com.atozdev.uptimemoniter.repository.jobRepositoryy.ServerJobRepository;
import com.atozdev.uptimemoniter.repository.triggerRepository.ServerTriggerRepository;
import com.atozdev.uptimemoniter.services.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ServerMoniterJob implements Job, JobHandler {

    private final ServerJobRepository serverJobRepository;
    private final ServerTriggerRepository serverTriggerRepository;

    public ServerMoniterJob(ServerJobRepository serverJobRepository
    ,ServerTriggerRepository serverTriggerRepository) {
        this.serverJobRepository = serverJobRepository;
        this.serverTriggerRepository = serverTriggerRepository;
    }

    @Override
    public JobType getJobType() {
        return JobType.SERVER;
    }

    @Override
    public void saveJob(Map<String, String> newjob) {

    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobKey jobKey = jobExecutionContext.getJobDetail().getKey();
        String jobName = jobKey.getName();
        ServerJob job = serverJobRepository.getByJobTitle(jobName);
        ServerTrigger trigger = new ServerTrigger();
        trigger.setJobTitle(job.getJobTitle());
        trigger.setTriggredAt(LocalDateTime.now());
        try {
            Process process = Runtime.getRuntime().exec("ping -c 1 " + job.getHostIp());
            long startTime = System.nanoTime();
            int returnVal = process.waitFor();
            long endTime = System.nanoTime();
            long durationInMillis = (endTime - startTime) / 1_000_000;
            trigger.setPingTime(durationInMillis);
            trigger.setJobStatus(returnVal == 0 ? JobStatus.UP : JobStatus.DOWN);
            serverTriggerRepository.save(trigger);
        } catch (Exception e) {
            log.error("Error while exec ping {} {} {}", jobName, e.getMessage(), e.getStackTrace());
            trigger.setJobStatus(JobStatus.DOWN);
            serverTriggerRepository.save(trigger);
        }
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
