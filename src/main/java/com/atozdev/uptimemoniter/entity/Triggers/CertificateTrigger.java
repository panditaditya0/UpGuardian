package com.atozdev.uptimemoniter.entity.Triggers;

import com.atozdev.uptimemoniter.enums.JobStatus;
import com.atozdev.uptimemoniter.enums.JobType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class CertificateTrigger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private JobType jobType = JobType.CERTIFICATE;
    private String jobTitle;
    private LocalDateTime triggredAt;
    private JobStatus jobStatus;
    private String isActive;

}
