package com.atozdev.uptimemoniter.enums;

import com.atozdev.uptimemoniter.services.JobImpl.*;
import lombok.Getter;
import org.quartz.Job;

public enum JobType {
    API(ApiMoniterJob.class, "REST APIs", "bi bi-gear"),
    SERVICE(ServiceMoniterJob.class, "Services", "bi bi-database-fill-gear"),
    SERVER(ServerMoniterJob.class, "Servers", "bi bi-hdd-rack"),
    CERTIFICATE(CertificateMoniterJob.class, "Domain Certificate", "fa-thin fa-globe"),
    DOCKER_SERVICE(DockerServiceMoniterJob.class, "Docker Services", "fa-brand fa-docker"),
    INVOICE(InvoiceMoniterJob.class, "Invoices", "bi bi-receipt");

    @Getter
    private final Class<? extends Job> jobClass;

    @Getter
    private final String jobName;

    @Getter
    private final String jobIcon;

    JobType(Class<? extends Job> jobClass, String jobName, String jobIcon) {
        this.jobClass = jobClass;
        this.jobName = jobName;
        this.jobIcon = jobIcon;
    }
}
