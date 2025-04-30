package com.atozdev.uptimemoniter.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlertCardItemDto {
    private String jobTitle;
    private String lastCheckedDate;
    private String batchColor;
    private String batchString;
    private String dotStatus;
}
