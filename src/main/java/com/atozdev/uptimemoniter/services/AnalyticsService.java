package com.atozdev.uptimemoniter.services;

import com.atozdev.uptimemoniter.dtos.AlertCardItemDto;
import com.atozdev.uptimemoniter.dtos.DashboardCardDto;
import com.atozdev.uptimemoniter.dtos.HeartbeatCardItemDto;
import com.atozdev.uptimemoniter.dtos.ResponseDto;
import com.atozdev.uptimemoniter.enums.IncidentLevel;
import com.atozdev.uptimemoniter.enums.JobType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AnalyticsService {
    private JobFactory jobFactory;


    public AnalyticsService(JobFactory jobFactory){
        this.jobFactory = jobFactory;
    }

    public DashboardCardDto fetchAllJobsByJobType(JobType jobType){
        try{
            JobHandler handler = jobFactory.getHandler(jobType);
            DashboardCardDto cardInfo = handler.fetchAllJobsTitles();
            return cardInfo;
        } catch (Exception ex){
            log.error("Error while fetchAllJobsByJobType "+  ex.getMessage() + ex.getStackTrace());
            throw new RuntimeException("Error while fetchAllJobsByJobType "+  ex.getMessage() + ex.getStackTrace());
        }
    }

    public List<HeartbeatCardItemDto> fetchAllUniqueHeartbeat(JobType jobType, int count) {
        try{
            PageRequest pageRequest = PageRequest.of(0, count);
            JobHandler handler = jobFactory.getHandler(jobType);
            List<HeartbeatCardItemDto> heartbeatCardItemDtoList = handler.fetchAllHeartBeats(pageRequest);
            return heartbeatCardItemDtoList;
        } catch (Exception ex){
            log.error("Error while fetchAllUniqueHeartbeat "+  ex.getMessage() + ex.getStackTrace());
            throw new RuntimeException("Error while fetchAllUniqueHeartbeat "+  ex.getMessage() + ex.getStackTrace());
        }
    }

    public List<AlertCardItemDto> fetchJobsByIncident(JobType jobType, IncidentLevel incedentLevel) {
            JobHandler handler = jobFactory.getHandler(jobType);
        return handler.fetchJobsByIncicentLevel(incedentLevel);
    }
}
