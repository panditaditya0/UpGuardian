package com.atozdev.uptimemoniter.services;

import com.atozdev.uptimemoniter.enums.JobType;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JobFactory {
    private final Map<JobType, JobHandler> handlerMap = new HashMap<>();

    public JobFactory(List<JobHandler> handlers) {
        for (JobHandler handler : handlers) {
            handlerMap.put(handler.getJobType(), handler);
        }
    }

    public JobHandler getHandler(JobType jobType) {
        return handlerMap.get(jobType);
    }
}
