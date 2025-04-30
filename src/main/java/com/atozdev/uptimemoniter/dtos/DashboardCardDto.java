package com.atozdev.uptimemoniter.dtos;

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
    private List <DashboardCardJobStatusDto> jobsStatus;
}
