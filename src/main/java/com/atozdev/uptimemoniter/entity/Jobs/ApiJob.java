package com.atozdev.uptimemoniter.entity.Jobs;
import com.atozdev.uptimemoniter.enums.JobType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiJob {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String jobTitle;
    private JobType jobType = JobType.API;

    @NonNull
    private String cronExpression;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @NonNull
    private String link;

}
