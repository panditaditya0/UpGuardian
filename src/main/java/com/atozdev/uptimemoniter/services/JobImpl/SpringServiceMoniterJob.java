package com.atozdev.uptimemoniter.services.JobImpl;

import com.atozdev.uptimemoniter.dtos.AlertCardItemDto;
import com.atozdev.uptimemoniter.dtos.DashboardCardDto;
import com.atozdev.uptimemoniter.dtos.DashboardCardJobStatusDto;
import com.atozdev.uptimemoniter.dtos.HeartbeatCardItemDto;
import com.atozdev.uptimemoniter.entity.Jobs.ApiJob;
import com.atozdev.uptimemoniter.entity.Jobs.MainJobs;
import com.atozdev.uptimemoniter.entity.Jobs.SpringServiceJob;
import com.atozdev.uptimemoniter.entity.Triggers.ApiTrigger;
import com.atozdev.uptimemoniter.entity.Triggers.SpringServiceTrigger;
import com.atozdev.uptimemoniter.enums.IncidentLevel;
import com.atozdev.uptimemoniter.enums.JobStatus;
import com.atozdev.uptimemoniter.enums.JobType;
import com.atozdev.uptimemoniter.enums.TimeRange;
import com.atozdev.uptimemoniter.repository.jobRepositoryy.ApiJobRepository;
import com.atozdev.uptimemoniter.repository.jobRepositoryy.SpringServiceJobRepository;
import com.atozdev.uptimemoniter.repository.triggerRepository.SpringServiceTriggerRepository;
import com.atozdev.uptimemoniter.services.JobHandler;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;


@Slf4j
@Service
public class SpringServiceMoniterJob implements Job, JobHandler {
    private final SpringServiceJobRepository springServiceJobRepository;
    private final SpringServiceTriggerRepository springServiceTriggerRepository;

    private static final OkHttpClient client = new OkHttpClient();
    private static final ArrayList<String> metricsLinkns = new ArrayList<>(
            Arrays.asList(
                    "application.started.time",
                    "disk.free"
            )
    );

    public SpringServiceMoniterJob(SpringServiceJobRepository springServiceJobRepository
    ,SpringServiceTriggerRepository springServiceTriggerRepository) {
        this.springServiceJobRepository = springServiceJobRepository;
        this.springServiceTriggerRepository = springServiceTriggerRepository;
    }

    @Override
    public JobType getJobType() {
        return JobType.SPRING_SERVICE;
    }

    @Override
    public void saveJob(Map<String, String> newjob) {
        SpringServiceJob job = new SpringServiceJob();
        job.setLink(newjob.get("link"));
        job.setCreatedAt(LocalDateTime.now());
        job.setUpdatedAt(LocalDateTime.now());
        job.setCronExpression(newjob.get("cronExpression"));
        job.setJobTitle(newjob.get("jobTitle"));
        springServiceJobRepository.save(job);
    }

    @Override
    public Object getJobsHistory() {
        return LastTimeRangeInfo(TimeRange.m5 , "UpGuardian Service");
    }

    @Override
    public Object getCurrentStatus() {
       return springServiceTriggerRepository.findTopByJobTitleOrderByTriggredAtDesc("UpGuardian Service");
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
        springServiceJobRepository.findAll().stream().forEach(job -> {
            DashboardCardJobStatusDto jobStatus = new DashboardCardJobStatusDto();
            jobStatus.setJobId(job.getId());
            jobStatus.setJobTitle(job.getJobTitle());
            SpringServiceTrigger trigger = springServiceTriggerRepository.findTopByJobTitleOrderByTriggredAtDesc(job.getJobTitle());
            if(null != trigger) {
                if (trigger.getJobStatus().equals(JobStatus.UP)){
                    jobStatus.setJobStatus(JobStatus.UP);
                    jobStatus.setStatusDot("status-dot up");
                } else{
                    jobStatus.setJobStatus(JobStatus.DOWN);
                    jobStatus.setStatusDot("status-dot down");
                }
                allJobStatus.add(jobStatus);
            }
        });
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
        return List.of();
    }

    @Override
    public List<HeartbeatCardItemDto> fetchAllHeartBeats(PageRequest count) {
        return List.of();
    }

    @Override
    public boolean validateNewJobForm(Map<String, String> newJob) {
        String metricsLink = newJob.get("link");
        JSONObject responseBody = executeGET(metricsLink);
        JSONArray listOfLinks = (JSONArray) responseBody.get("names");
        ArrayList<String> listOfAllLinks = new ArrayList<>();
        listOfLinks.forEach(x -> {
            listOfAllLinks.add(x.toString());
        });

        return listOfAllLinks.containsAll(metricsLinkns);
    }

    private JSONObject executeGET(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            JSONObject body = response.body() != null ? new JSONObject(response.body().string()) : null;
           return body;
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobKey jobKey = context.getJobDetail().getKey();
        String jobTitle = jobKey.getName();
        SpringServiceJob job = springServiceJobRepository.findByJobTitle(jobTitle);
        try {
            BigDecimal upTime = fetchValue(job.getLink() + "/process.uptime");
            BigDecimal noOfCores = fetchValue(job.getLink() + "/system.cpu.count");
            BigDecimal cpuUsage = fetchValue(job.getLink() + "/system.cpu.usage")
                    .multiply(BigDecimal.valueOf(100));
            BigDecimal ramUsed = fetchValue(job.getLink() + "/jvm.memory.used");
            BigDecimal ramAvaiable = fetchValue(job.getLink() + "/jvm.memory.committed");
            BigDecimal systemLoad1m = fetchValue(job.getLink() + "/system.load.average.1m");
            BigDecimal ramPercentage = ramUsed
                    .divide(ramAvaiable, 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
            BigDecimal freeDisk = fetchValue(job.getLink() + "/disk.free");
            BigDecimal bytesInGB = new BigDecimal(1024L * 1024L * 1024L);
            BigDecimal diskFreeGB = freeDisk.divide(bytesInGB, 2, RoundingMode.HALF_UP);
            SpringServiceTrigger aTrigger = new SpringServiceTrigger();
            aTrigger.setCpuCores(noOfCores);
            aTrigger.setJobTitle(jobTitle);
            aTrigger.setJobStatus(JobStatus.UP);
            aTrigger.setIncidentLevel(IncidentLevel.NONE);
            aTrigger.setCpuCores(noOfCores);
            aTrigger.setCpuUsage(cpuUsage);
            aTrigger.setRamUsage(ramPercentage);
            aTrigger.setFreeDisk(diskFreeGB);
            aTrigger.setUpTime(upTime);
            aTrigger.setTriggredAt(LocalDateTime.now());
            aTrigger.setSystemLoad1m(systemLoad1m);
            springServiceTriggerRepository.save(aTrigger);
        } catch (Exception ex) {
            log.error("Error while trigger " + jobTitle + " " + ex.getMessage());
        }
    }

    private BigDecimal fetchValue(String link){
        return  (BigDecimal) ((JSONObject)((JSONArray) executeGET(link).get("measurements")).get(0)).get("value");
    }

    private Object LastTimeRangeInfo(TimeRange timeRange, String jobTitle){
        LocalDateTime fromTime;
        if(timeRange.getTimeUnit().equals("minutes")){
            fromTime = LocalDateTime.now().minusMinutes(timeRange.getTimeValue());
        } else if (timeRange.getTimeUnit().equals("hours")) {
            fromTime = LocalDateTime.now().minusHours(timeRange.getTimeValue());
        } else {
            fromTime = LocalDateTime.now().minusDays(timeRange.getTimeValue());
        }
        return springServiceTriggerRepository.findTriggersSince(fromTime, jobTitle);
    }
}
