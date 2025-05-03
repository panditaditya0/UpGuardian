package com.atozdev.uptimemoniter.services;

import com.atozdev.uptimemoniter.dtos.CommonJobDto;
import com.atozdev.uptimemoniter.entity.Jobs.MainJobs;
import com.atozdev.uptimemoniter.enums.JobType;
import com.atozdev.uptimemoniter.exceptions.JobNotFoundException;
import com.atozdev.uptimemoniter.repository.jobRepositoryy.*;
import com.sun.tools.javac.Main;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class JobService {
    private final Scheduler schedular;
    private final JobFactory jobFactory;
    private final MainJobRepo mainJobRepo;
    static Logger logger = LoggerFactory.getLogger(JobService.class);

    public JobService(Scheduler schedular
            , JobFactory jobFactory
            , MainJobRepo mainJobRepo
            , ApiJobRepository apiJobRepository) {
        this.schedular = schedular;
        this.jobFactory = jobFactory;
        this.mainJobRepo = mainJobRepo;
        apiJobRepository.findAll();
    }

    private boolean scheduleAJob(CommonJobDto job) {
        try {
            JobDetail jobDetail = JobBuilder.newJob(job.getJobType().getJobClass())
                    .withIdentity(job.getJobTitle(), job.getJobType().getJobClass().toString())
                    .storeDurably()
                    .build();
            TriggerKey triggerKey = new TriggerKey(job.getJobTitle(), job.getJobType().toString());
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(triggerKey)
                    .withSchedule(CronScheduleBuilder.cronSchedule(job.getCronExpression()))
                    .build();
            schedular.scheduleJob(jobDetail, trigger);
            return true;
        } catch (Exception ex) {
            logger.error("Error while scheduling for {} -> {} ", job.getJobTitle(), ex.getMessage());
        }
        return false;
    }

    @PostConstruct
    public void scheduleJobFromDbInit() throws ClassNotFoundException, SchedulerException {
        List<MainJobs> jobs = mainJobRepo.findAll();
        for (MainJobs job : jobs) {
            try {
                CommonJobDto commonJobDto = new CommonJobDto();
                commonJobDto.setJobType(job.getJobType());
                commonJobDto.setJobTitle(job.getJobTitle());
                commonJobDto.setCronExpression(job.getCronExpression());
//                this.scheduleAJob(commonJobDto);
            } catch (Exception ex) {
            }
        }
    }

    @Transactional
    public boolean addAJob(Map<String, String> newJob) {
        CommonJobDto job = new CommonJobDto();
        job.setJobTitle(newJob.get("jobTitle"));
        job.setJobType(JobType.valueOf(newJob.get("jobType")));
        job.setCronExpression(newJob.get("cronExpression"));
        boolean isJobScheduled = scheduleAJob(job);
        if (isJobScheduled) {
            JobHandler jobHandler = jobFactory.getHandler(JobType.valueOf(newJob.get("jobType")));
            MainJobs mainJobs = new MainJobs();
            mainJobs.setJobTitle(job.getJobTitle());
            mainJobs.setJobType(job.getJobType());
            mainJobs.setCronExpression(job.getCronExpression());
            mainJobRepo.save(mainJobs);
            jobHandler.saveJob(newJob);
            return true;
        }
        return false;
    }

    public List<CommonJobDto> allJobs() {
        try {
            List<CommonJobDto> jobs = new ArrayList<>();
            for (JobType type : JobType.values()) {
                for (JobKey jobKey : schedular.getJobKeys(GroupMatcher.jobGroupEquals(type.toString()))) {
//                    jobs.add(mainJobRepo.findByJobTitle(jobKey.toString()));
                }
            }
            return jobs;
        } catch (Exception ex) {
            throw new RuntimeException(" Error while fetching all jobs" + ex.getMessage());
        }
    }

    private MainJobs findJobById(Long oldJobId) {
        Optional<MainJobs> mainJobsOptional = mainJobRepo.findById(oldJobId);
        if (mainJobsOptional.isEmpty()) {
            throw new RuntimeException("Job not present in db");
        }
        return mainJobsOptional.get();
    }

    public void updateJob(Map<String, String> job, Long oldJobId) {
        try {
            MainJobs oldjob = findJobById(oldJobId);
            MainJobs newJob = new MainJobs();
            newJob.setJobType(JobType.valueOf(job.get("jobType")));
            newJob.setJobTitle(job.get("jobTitle"));
            newJob.setCronExpression(job.get("cronExpression"));

            TriggerKey oldTriggerKey = new TriggerKey(oldjob.getJobTitle(), oldjob.getJobType().toString());
            TriggerKey newTriggerKey = new TriggerKey(oldjob.getJobTitle(), oldjob.getJobType().toString());
            Trigger newTrigger = TriggerBuilder.newTrigger()
                    .withIdentity(newTriggerKey)
                    .withSchedule(CronScheduleBuilder.cronSchedule(newJob.getCronExpression()))
                    .build();
            schedular.rescheduleJob(oldTriggerKey, newTrigger);
            MainJobs oldJob = mainJobRepo.findByJobTitle(newJob.getJobTitle());
            oldJob.setCronExpression(newJob.getCronExpression());
            oldJob.setJobTitle(newJob.getJobTitle());
            oldJob.setJobType(newJob.getJobType());
            mainJobRepo.save(oldJob);
            JobHandler handler = jobFactory.getHandler(oldJob.getJobType());
            handler.updateJob(job, oldJob.getJobTitle());
        } catch (Exception ex) {
            throw new RuntimeException("Error while Re-scheduling job " + ex.getMessage());
        }
    }

    public void deleteJob(Long id) {
        try {
            MainJobs mainJob = findJobById(id);
            JobKey jobKey = new JobKey(mainJob.getJobTitle(), mainJob.getJobType().toString());
            schedular.deleteJob(jobKey);
            mainJobRepo.delete(mainJob);
            jobFactory.getHandler(mainJob.getJobType())
                    .delete(mainJob);
            throw new JobNotFoundException("Job not found in DB");
        } catch (Exception ex) {
            throw new RuntimeException("Error while Deleting job " + id + " " + ex.getMessage());
        }
    }

    public void pauseJob(Long id) {
        try {
            MainJobs job = findJobById(id);
            JobKey jobKey = new JobKey(job.getJobTitle(), job.getJobType().toString());
            schedular.pauseJob(jobKey);
            throw new JobNotFoundException("Job not found in DB");
        } catch (Exception ex) {
            throw new RuntimeException("Error while pausing job " + id + " " + ex.getMessage());
        }
    }

    public void resumeJob(Long id) {
        try {
            MainJobs job = findJobById(id);
            JobKey jobKey = new JobKey(job.getJobTitle(), job.getJobType().toString());
            schedular.resumeJob(jobKey);
        } catch (Exception ex) {
            throw new RuntimeException("Error while resume job " + id + " " + ex.getMessage());
        }
    }

    public void triggerJobById(Long id) {
        try {
            MainJobs job = findJobById(id);
            JobKey jobKey = new JobKey(job.getJobTitle(), job.getJobType().getJobClass().toString());
            schedular.triggerJob(jobKey);
        } catch (Exception ex) {
            throw new RuntimeException("Error while triggering job " + id + " " + ex.getMessage());
        }
    }
}