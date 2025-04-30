package com.atozdev.uptimemoniter.entity.Jobs;

import com.atozdev.uptimemoniter.enums.JobStatus;
import com.atozdev.uptimemoniter.enums.JobType;
import com.atozdev.uptimemoniter.enums.RecordType;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertificateJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String jobTitle;
    private JobType jobType = JobType.CERTIFICATE;
    private String cronExpression;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private RecordType recordType;
    private String resolverName;
    private String hostName;
}
