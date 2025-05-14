package com.atozdev.uptimemoniter.entity.Triggers;

import com.atozdev.uptimemoniter.enums.IncidentLevel;
import com.atozdev.uptimemoniter.enums.JobStatus;
import com.atozdev.uptimemoniter.enums.JobType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class SpringServiceTrigger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private JobType jobType = JobType.SPRING_SERVICE;
    private String jobTitle;
    private LocalDateTime triggredAt;
    private JobStatus jobStatus;

    private IncidentLevel incidentLevel;
    private BigDecimal cpuCores;
    private BigDecimal cpuUsage;
    private BigDecimal ramUsage;
    private BigDecimal freeDisk;
    private BigDecimal upTime;
    private BigDecimal systemLoad1m;
}
