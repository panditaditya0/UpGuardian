package com.atozdev.uptimemoniter.controller;

import com.atozdev.uptimemoniter.dtos.ResponseDto;
import com.atozdev.uptimemoniter.entity.Jobs.MainJobs;
import com.atozdev.uptimemoniter.enums.IncidentLevel;
import com.atozdev.uptimemoniter.enums.JobType;
import com.atozdev.uptimemoniter.repository.jobRepositoryy.MainJobRepo;
import com.atozdev.uptimemoniter.services.AnalyticsService;
import com.atozdev.uptimemoniter.services.JobFactory;
import com.atozdev.uptimemoniter.services.JobHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/analytics")
@CrossOrigin
public class AnalyticsController {
    private final JobFactory jobFactory;
    private final MainJobRepo mainJobRepo;
    private final AnalyticsService analyticsService;

    public AnalyticsController(JobFactory jobFactory
    , MainJobRepo mainJobRepo
    ,AnalyticsService analyticsService) {
        this.jobFactory = jobFactory;
        this.mainJobRepo = mainJobRepo;
        this.analyticsService = analyticsService;
    }

    @GetMapping("/current/{id}")
    public ResponseEntity<ResponseDto> currentStatus(@PathVariable Long id){
        Optional<MainJobs> jobInfo = mainJobRepo.findById(id);
        JobHandler jobService = jobFactory.getHandler(jobInfo.get().getJobType());
        Object status = jobService.getCurrentStatus();
        return new ResponseEntity<ResponseDto>(new ResponseDto(true, status, ""), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> fetchHistory(@PathVariable Long id) {
        Optional<MainJobs> jobInfo = mainJobRepo.findById(id);
        JobHandler jobService = jobFactory.getHandler(jobInfo.get().getJobType());
        Object status = jobService.getJobsHistory();
        return new ResponseEntity<ResponseDto>(new ResponseDto(true, status, ""), HttpStatus.OK);
    }

    @GetMapping("/jobType")
    public ResponseEntity<ResponseDto> fetchAllJobTypes(){
        return new ResponseEntity<>(new ResponseDto(true, JobType.values(), ""), HttpStatus.OK);
    }
}
