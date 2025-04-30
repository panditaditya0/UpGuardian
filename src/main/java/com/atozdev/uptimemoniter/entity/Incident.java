package com.atozdev.uptimemoniter.entity;


import com.atozdev.uptimemoniter.enums.IncidentLevel;
import com.atozdev.uptimemoniter.enums.JobType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Incident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private JobType jobType;
    private LocalDate incidentTime;
    private Long jobId;
    private IncidentLevel incidentLevel;
}
