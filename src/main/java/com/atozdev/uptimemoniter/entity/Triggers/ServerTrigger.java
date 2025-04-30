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
public class ServerTrigger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private JobType jobType = JobType.SERVER;
    private String jobTitle;
    private LocalDateTime triggredAt;
    private JobStatus jobStatus;
    private Long pingTime;
}
