package com.atozdev.uptimemoniter.controller;

import com.atozdev.uptimemoniter.dtos.*;
import com.atozdev.uptimemoniter.enums.IncidentLevel;
import com.atozdev.uptimemoniter.enums.JobType;
import com.atozdev.uptimemoniter.services.AnalyticsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/analytics")
public class DashboardAnalyticsController {

    private final AnalyticsService analyticsService;

    public DashboardAnalyticsController(AnalyticsService analyticsService){
        this.analyticsService = analyticsService;
    }

    @GetMapping("/alerts")
    public ResponseEntity<ResponseDto> fetchAlerts(){
        ArrayList<AlertCardItemDto> alertCardItemDtos = new ArrayList<>();
        Arrays.stream(JobType.values()).forEach(x -> {
            alertCardItemDtos.addAll(analyticsService.fetchJobsByIncident(x, IncidentLevel.SEVERE));
        });
        alertCardItemDtos.stream()
                .sorted(Comparator.comparing(AlertCardItemDto::getLastCheckedDate).reversed())
                .collect(Collectors.toList());
        return new ResponseEntity<>(new ResponseDto(true, alertCardItemDtos, ""), HttpStatus.OK);
    }

    @GetMapping("/card-status")
    public ResponseEntity<ResponseDto> fetchEachCardStatus(){
        List<DashboardCardDto> dashboardCardDtoList = new ArrayList<>();
        Arrays.stream(JobType.values()).forEach(x -> {
            dashboardCardDtoList.add(analyticsService.fetchAllJobsByJobType(x));
        });

        dashboardCardDtoList.stream()
                .sorted(Comparator.comparing(DashboardCardDto::getJobTypeName))
                .collect(Collectors.toList());
        return new ResponseEntity<>(new ResponseDto(true, dashboardCardDtoList, ""), HttpStatus.OK);
    }

    @GetMapping("/heartbeat/{count}")
    public ResponseEntity<ResponseDto> fetchEachJobStatus(@PathVariable int count){
        List<HeartbeatCardItemDto> heartbeatCardItemDtos = new ArrayList<>();
        Arrays.stream(JobType.values()).forEach(x -> {
            heartbeatCardItemDtos.addAll(analyticsService.fetchAllUniqueHeartbeat(x, count));
        });
        heartbeatCardItemDtos.stream()
                .sorted(Comparator.comparing(HeartbeatCardItemDto::getLastCheckedDate).reversed())
                .collect(Collectors.toList());

        return new ResponseEntity<>(new ResponseDto(true, heartbeatCardItemDtos, ""), HttpStatus.OK);
    }
}
