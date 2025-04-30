package com.atozdev.uptimemoniter.entity.Jobs;

import com.atozdev.uptimemoniter.enums.JobStatus;
import com.atozdev.uptimemoniter.enums.JobType;
import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceJob {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String jobTitle;
    private String cronExpression;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private LocalDateTime alertDate;
    private JobType jobType = JobType.INVOICE;

}
