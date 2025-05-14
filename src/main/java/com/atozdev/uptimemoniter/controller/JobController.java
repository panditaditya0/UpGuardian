package com.atozdev.uptimemoniter.controller;

import com.atozdev.uptimemoniter.dtos.CommonJobDto;
import com.atozdev.uptimemoniter.dtos.ResponseDto;
import com.atozdev.uptimemoniter.enums.JobType;
import com.atozdev.uptimemoniter.services.JobService;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/scheduler/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService){
        this.jobService = jobService;
    }

    @GetMapping
    public ResponseEntity<ResponseDto> getAllJobs(){
        List<CommonJobDto> jobs = jobService.allJobs();
        return new ResponseEntity<>(new ResponseDto(true, jobs, ""), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDto> addAJob(@RequestBody Map<String, String> job){
        boolean isScheduled = jobService.addAJob(job);
        if (!isScheduled)  return new ResponseEntity<>(new ResponseDto(false, "", ""), HttpStatus.ACCEPTED);
        return new ResponseEntity<>(new ResponseDto(true, "", ""), HttpStatus.ACCEPTED);
    }

    @PostMapping("/job/{id}")
    public ResponseEntity<ResponseDto> editJob(@RequestBody Map<String, String> job, @RequestParam Long id){
        jobService.updateJob(job, id);
        return new ResponseEntity<>(new ResponseDto(true, "", ""), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/job/{id}")
    public ResponseEntity<ResponseDto> deleteJob(@RequestParam Long id){
        jobService.deleteJob(id);
        return new ResponseEntity<>(new ResponseDto(true, "", ""), HttpStatus.ACCEPTED);
    }

    @GetMapping("/job/pause/{id}")
    public ResponseEntity<ResponseDto> pauseJob(@RequestParam Long id){
        jobService.pauseJob(id);
        return new ResponseEntity<>(new ResponseDto(true, "", ""), HttpStatus.ACCEPTED);
    }

    @GetMapping("/job/resume/{id}")
    public ResponseEntity<ResponseDto> resumeJob(@RequestParam Long id){
        jobService.resumeJob(id);
        return new ResponseEntity<>(new ResponseDto(true, "", ""), HttpStatus.ACCEPTED);
    }

    @GetMapping("/job/trigger/{id}")
    public ResponseEntity<ResponseDto> triggerJob(@PathVariable Long id){
        jobService.triggerJobById(id);
        return new ResponseEntity<>(new ResponseDto(true, "", ""), HttpStatus.ACCEPTED);
    }

    @PostMapping("/job/validate/{jobType}")
    public ResponseEntity<ResponseDto> validateNewJob(@RequestBody Map<String, String> job, @PathVariable JobType jobType){
        boolean isFormValid = jobService.validateNewJob(job, jobType);
        if (isFormValid){
            return new ResponseEntity<>(new ResponseDto(true, "", ""), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDto(false, "", "Some of the Metrics are not available"), HttpStatus.BAD_REQUEST);
    }
}
