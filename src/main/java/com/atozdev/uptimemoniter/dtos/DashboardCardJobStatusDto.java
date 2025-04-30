package com.atozdev.uptimemoniter.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardCardJobStatusDto{
    private Long jobId;
    private String jobTitle;
    private Object jobStatus;
    private String statusDot;
}
