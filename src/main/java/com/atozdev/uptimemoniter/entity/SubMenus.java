package com.atozdev.uptimemoniter.entity;

import com.atozdev.uptimemoniter.enums.JobStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class SubMenus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long subMenuId;

    private String title;
    private String route;
    private JobStatus status;
}
