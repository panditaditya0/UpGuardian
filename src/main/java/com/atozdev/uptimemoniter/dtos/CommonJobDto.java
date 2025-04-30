package com.atozdev.uptimemoniter.dtos;

import com.atozdev.uptimemoniter.enums.JobType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommonJobDto {
    private String jobTitle;
    private JobType jobType;
    private String cronExpression;
}

