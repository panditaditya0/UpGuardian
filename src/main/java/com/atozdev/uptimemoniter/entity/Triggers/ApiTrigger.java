package com.atozdev.uptimemoniter.entity.Triggers;

import com.atozdev.uptimemoniter.enums.IncidentLevel;
import com.atozdev.uptimemoniter.enums.JobStatus;
import com.atozdev.uptimemoniter.enums.JobType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ApiTrigger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private JobType jobType = JobType.API;
    private String jobTitle;
    private LocalDateTime triggredAt;
    private JobStatus jobStatus;
    private Long responseTime;
    private Long upTimeDay;
    private Long upTimeMonth;
    private Long upTimeYear;


    @NonNull
    private HttpStatus jobStatusCode;

    private IncidentLevel incidentLevel;
}
