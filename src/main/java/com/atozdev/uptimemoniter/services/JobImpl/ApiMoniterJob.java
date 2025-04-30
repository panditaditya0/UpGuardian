package com.atozdev.uptimemoniter.services.JobImpl;

import com.atozdev.uptimemoniter.dtos.AlertCardItemDto;
import com.atozdev.uptimemoniter.dtos.DashboardCardDto;
import com.atozdev.uptimemoniter.dtos.DashboardCardJobStatusDto;
import com.atozdev.uptimemoniter.dtos.HeartbeatCardItemDto;
import com.atozdev.uptimemoniter.entity.Jobs.ApiJob;
import com.atozdev.uptimemoniter.entity.Jobs.MainJobs;
import com.atozdev.uptimemoniter.entity.Triggers.ApiTrigger;
import com.atozdev.uptimemoniter.enums.IncidentLevel;
import com.atozdev.uptimemoniter.enums.JobStatus;
import com.atozdev.uptimemoniter.enums.JobType;
import com.atozdev.uptimemoniter.repository.jobRepositoryy.ApiJobRepository;
import com.atozdev.uptimemoniter.repository.triggerRepository.ApiTriggerRepository;
import com.atozdev.uptimemoniter.services.JobHandler;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.quartz.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class ApiMoniterJob implements Job, JobHandler {
    private final ApiJobRepository apiJobRepository;
    private final ApiTriggerRepository apiTriggerRepository;

    public ApiMoniterJob(ApiJobRepository apiJobRepository
            , ApiTriggerRepository apiTriggerRepository) {
        this.apiJobRepository = apiJobRepository;
        this.apiTriggerRepository = apiTriggerRepository;
    }

    @Override
    public void execute(JobExecutionContext context) {
        JobKey jobKey = context.getJobDetail().getKey();
        String jobTitle = jobKey.getName();
        ApiJob job = apiJobRepository.findByJobTitle(jobTitle);
        long startTime = System.nanoTime();
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .build();

        Request req = new Request.Builder()
                .url(job.getLink())
                .build();
        try (Response response = client.newCall(req).execute()) {
            long endTime = System.nanoTime();
            long durationInMs = (endTime - startTime) / 1_000_000;
            HttpStatus status = HttpStatus.resolve(response.code());

            JobStatus jobStatus = response.isSuccessful() ? JobStatus.UP : JobStatus.DOWN;
            ApiTrigger apiTrigger = new ApiTrigger();
            apiTrigger.setResponseTime(durationInMs);
            apiTrigger.setJobTitle(jobTitle);
            apiTrigger.setJobStatus(jobStatus);
            apiTrigger.setTriggredAt(LocalDateTime.now());
            apiTrigger.setJobStatusCode(status);
            apiTrigger.setIncidentLevel(response.isSuccessful() ? IncidentLevel.NONE : IncidentLevel.SEVERE);
            apiTriggerRepository.save(apiTrigger);
            log.info("Response for {} Status {}  body {}", jobTitle, response.code(), response.body());
        } catch (Exception ex) {
            log.error("Error while executing API job " + ex.getMessage());
        }
    }

    @Override
    public Object getJobsHistory() {
        return apiTriggerRepository.findTop5ByOrderByTriggredAtDesc();
    }

    @Override
    public Object getCurrentStatus() {
        return apiTriggerRepository.findTopByOrderByTriggredAtDesc();
    }

    @Override
    public boolean updateJob(Map<String, String> newJobDto, String oldJobTitle) {
        try{
            ApiJob oldJob = apiJobRepository.findByJobTitle(oldJobTitle);
            oldJob.setLink(newJobDto.get("link"));
            oldJob.setUpdatedAt(LocalDateTime.now());
            oldJob.setCronExpression(newJobDto.get("jobType"));
            oldJob.setJobTitle(newJobDto.get("jobTitle"));
            return true;
        } catch (Exception ex){
            log.error("Error while updateJob API -> " + ex.getMessage() + ex.getStackTrace());
        }
        return false;
    }

    @Override
    public boolean delete(MainJobs dto) {
        ApiJob apiJob = apiJobRepository.findByJobTitle(dto.getJobTitle());
        apiJobRepository.delete(apiJob);
        return true;
    }

    @Override
    public DashboardCardDto fetchAllJobsTitles() {
        List<DashboardCardJobStatusDto> allJobStatus = new ArrayList<DashboardCardJobStatusDto>();
        apiJobRepository.findAll().stream().forEach(job -> {
            DashboardCardJobStatusDto jobStatus = new DashboardCardJobStatusDto();
            jobStatus.setJobId(job.getId());
            jobStatus.setJobTitle(job.getJobTitle());
            ApiTrigger trigger = apiTriggerRepository.findTopByJobTitleOrderByTriggredAtDesc(job.getJobTitle());
            HttpStatus triggedJobStatus = trigger.getJobStatusCode();
            jobStatus.setJobStatus(triggedJobStatus.value() + " " + triggedJobStatus.getReasonPhrase());
            if (triggedJobStatus.value() < 300){
                jobStatus.setStatusDot("status-dot up");
            } else if (triggedJobStatus.value() < 500){
                jobStatus.setStatusDot("status-dot down");
            } else{
                jobStatus.setStatusDot("status-dot down");
            }
            allJobStatus.add(jobStatus);
        });
        return new DashboardCardDto(
                this.getJobType().getJobName(),
                this.getJobType().getJobIcon(),
                allJobStatus);
    }

    @Override
    public Object fetchJobsByStatus(JobStatus status) {

        return apiTriggerRepository.findAllByJobStatusNot(status);
    }

    @Override
    public List<AlertCardItemDto> fetchJobsByIncicentLevel(IncidentLevel incedentLevel) {
        List<ApiTrigger> apiTrigger =  apiTriggerRepository.findAllByIncedent(incedentLevel);
        List<AlertCardItemDto> alertCardItemDtos = new ArrayList<>();
        apiTrigger.stream().forEach(x -> {
            alertCardItemDtos.add(convertApiTriggerToAlertCardItem(x));
        });
        return alertCardItemDtos;
    }

    @Override
    public List<HeartbeatCardItemDto> fetchAllHeartBeats(PageRequest pageRequest) {
        List<ApiTrigger> apiTriggersList = apiTriggerRepository.findAllByOrderByTriggredAtDesc(pageRequest);
        List<HeartbeatCardItemDto> heartbeatCardItemDtoList = new ArrayList<>() ;
        apiTriggersList.stream().forEach(x -> {
            heartbeatCardItemDtoList.add(convertApiTriggerToHeartBeatCardItem(x));
        });

        return heartbeatCardItemDtoList;
    }

    @Override
    public JobType getJobType() {
        return JobType.API;
    }

    @Override
    public void saveJob(Map<String, String> newjob) {
        ApiJob job = new ApiJob();
        job.setLink(newjob.get("link"));
        job.setCreatedAt(LocalDateTime.now());
        job.setUpdatedAt(LocalDateTime.now());
        job.setCronExpression(newjob.get("cronExpression"));
        job.setJobTitle(newjob.get("jobTitle"));
        apiJobRepository.save(job);
    }


    private AlertCardItemDto convertApiTriggerToAlertCardItem(ApiTrigger apiTrigger){
        AlertCardItemDto alertCardItemDto = new AlertCardItemDto();
        HttpStatus status = apiTrigger.getJobStatusCode();
        alertCardItemDto.setLastCheckedDate(apiTrigger.getTriggredAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        alertCardItemDto.setBatchString(status.value() + " " + status.getReasonPhrase());
        alertCardItemDto.setBatchColor(status.is2xxSuccessful()? "badge bg-success" : "badge bg-danger");
        alertCardItemDto.setJobTitle(apiTrigger.getJobTitle());
        alertCardItemDto.setDotStatus(status.is2xxSuccessful()? "status-dot down" : "status-dot down");
        return alertCardItemDto;
    }

    private HeartbeatCardItemDto convertApiTriggerToHeartBeatCardItem(ApiTrigger apiTrigger){
        HeartbeatCardItemDto heartbeatCardItemDto = new HeartbeatCardItemDto();
        heartbeatCardItemDto.setJobTitle(apiTrigger.getJobTitle());
        heartbeatCardItemDto.setLastCheckedDate(apiTrigger.getTriggredAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        HttpStatus status = apiTrigger.getJobStatusCode();
        heartbeatCardItemDto.setBatchString(status.value() + " " + status.getReasonPhrase());
        heartbeatCardItemDto.setDotStatus(status.is2xxSuccessful()? "status-dot down" : "status-dot down");
        heartbeatCardItemDto.setBatchColor(status.is2xxSuccessful()? "badge bg-success" : "badge bg-danger");
        return heartbeatCardItemDto;
    }
}