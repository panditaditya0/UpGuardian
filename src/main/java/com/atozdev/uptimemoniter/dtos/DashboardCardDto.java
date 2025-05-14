package com.atozdev.uptimemoniter.dtos;

import com.atozdev.uptimemoniter.enums.JobType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardCardDto {
    private String jobTypeName;
    private String jobIcon;
    private JobType jobType;
    private List <DashboardCardJobStatusDto> jobsStatus;
}
